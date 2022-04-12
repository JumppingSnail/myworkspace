<template>
    <div class="hello">
        <van-nav-bar :fixed=true @click-left="onClickLeft" :title="title" />
        <div class="info-box" :style="is_weixin?'top:46px;':'top:46px;'">
            <p class="title">{{ title }}</p>
            <div class="operation">
                <div>
                    <div v-for="(site,index) in classArr" :key="index">
                        <span v-if="cat_name == site.name" class="tab-zy" :class="cat_name == site.name ? site.class : ''">{{ cat_name }}</span>
                    </div>
                </div>
                <div>
                    <img v-if="cat_name == '书籍'" src="../../../static/img/menu.png" @click="menuBtn" alt="">
                </div>
            </div>
        </div>
        <div class="content" :style="is_weixin?'padding-top: 137px;':'padding-top: 137px;'">
            <div class="detail-box">
                <div v-if="menuActive == 0" class="info">
                    {{ info }}
                </div>
                <div v-for="(item,index) in dataList" :key="index">
                    <p class="title">{{item.section_name}}</p>
                    <div class="info" v-html="item.section_content"></div>
                </div>
            </div>
            <van-action-sheet v-model="show" :title="title">
                <div class="menuContent">
                    <van-tabs v-model="active">
                        <van-tab title="目录">
                            <div v-for="(item,index) in menuList" :key="index">
                                <p v-if="menuActive==index" class="menuActive chapter" :class="item.level == 2?'section':''" :style="index == menuList.length-1?'border:none;':''" @click="getSectionContent(index)">{{ item.sectionName }}</p>
                                <p v-else :class="item.level == 2?'section':''" :style="index == menuList.length-1?'border:none;':''" class="chapter" @click="getSectionContent(index)">{{ item.sectionName }}</p>
                            </div>
                        </van-tab>
                    </van-tabs>
                </div>
            </van-action-sheet>
            <van-tabbar  v-if="cat_name == '书籍'" v-model="tabActive">
                <van-tabbar-item @click="tabClick">上一章</van-tabbar-item>
                <van-tabbar-item @click="tabClick">下一章</van-tabbar-item>
            </van-tabbar>
        </div>
    </div>
</template>

<script>

	import {Dialog} from "vant";

	export default {
		data() {
			return {
				is_weixin: this.is_weixin(),
				title: '',
				id: this.$route.query.id,
				sectionId: this.$route.query.sectionId?this.$route.query.sectionId:'',
				collect_flag: '0',
				cat_name: '',
				info: '',
				dataList: [],
				menuList: [],
				classArr: [],
				bookmarkList: [],
				show: false,
				active: 0,
				menuActive: 0,
				tabActive: 1
			};
		},
		created() {
			// 获取数据
			this.getDetail()
			this.getNavList()
			this.getMenu()
			if (this.is_weixin) {
				document.title = ''
			}
		},
		methods: {
			//登录
			onClickLeft() {
				this.$router.go(-1)
			},
			// 获取菜单列表
			getNavList () {
				this.$post('front/allCatagorylist.do').then((e) => {
					if (e.result === 'ok') {
						e.data.forEach((item,index)=>{
							if (index%4 == 0) {
								this.classArr.push({'name':item.cat_name,'class':'tab-zy1'})
							} else if(index%4 == 1){
								this.classArr.push({'name':item.cat_name,'class':'tab-zy2'})
							} else if(index%4 == 2){
								this.classArr.push({'name':item.cat_name,'class':'tab-zy3'})
							} else if(index%4 == 3){
								this.classArr.push({'name':item.cat_name,'class':'tab-zy4'})
							}
						})
					} else {
						this.$toast('服务器异常，请联系管理员');
					}
				}).catch(() => {
					let _this = this
					Dialog.alert({
						title: '提示',
						message: '登录失效，重新登录',
					}).then(() => {
						_this.$router.push({path:'/login'});
					});
				})
			},
			getDetail () {
				this.$post('front/fetchKnowledgeMainInfo.do', {
					'reposId': this.id
				}).then((e) => {
					if (e.result === 'ok') {
						document.title = e.data.repos_title
						this.title = e.data.repos_title
						this.collect_flag = e.data.collect_flag
						this.info = e.data.repos_digest
						this.cat_name = e.data.cat_name
					} else {
						this.$toast('服务器异常，请联系管理员');
					}
				}).catch(() => {
					let _this = this
					Dialog.alert({
						title: '提示',
						message: '登录失效，重新登录',
					}).then(() => {
						_this.$router.push({path:'/login'});
					});
				})
			},
			getData () {
				if (this.sectionId) {
					this.menuList.forEach((item,index)=>{
						if (item.sectionId == this.sectionId) {
							this.menuActive = index
						}
					})
				}
				this.$post('front/fetchKnowledgeContent.do', {
					'reposId': this.id,
					'sectionId': this.menuList.length > 0 && this.cat_name == '书籍' ? this.menuList[this.menuActive].sectionId : ''
				}).then((e) => {
					if (e.result === 'ok') {
						this.dataList = e.data
					} else {
						this.$toast('服务器异常，请联系管理员');
					}
				}).catch(() => {
					let _this = this
					Dialog.alert({
						title: '提示',
						message: '登录失效，重新登录',
					}).then(() => {
						_this.$router.push({path:'/login'});
					});
				})
			},
			// 收藏
			collection () {
				this.$post('front/saveOrDeleteMyCollect.do', {
					'reposId': this.id
				}).then((e) => {
					if (e.result === 'ok') {
						Dialog.alert({
							title: '',
							message: '已添加至收藏',
							confirmButtonColor: '#0067FF'
						}).then(() => {
							this.collect_flag = '1'
						})
					} else {
						this.$toast('服务器异常，请联系管理员');
					}
				}).catch(() => {
					let _this = this
					Dialog.alert({
						title: '提示',
						message: '登录失效，重新登录',
					}).then(() => {
						_this.$router.push({path:'/login'});
					});
				})
			},
			collectionCancel () {
				this.$post('front/saveOrDeleteMyCollect.do', {
					'reposId': this.id
				}).then((e) => {
					if (e.result === 'ok') {
						Dialog.alert({
							title: '',
							message: '已取消收藏',
							confirmButtonColor: '#0067FF'
						}).then(() => {
							this.collect_flag = '0'
						})
					} else {
						this.$toast('服务器异常，请联系管理员');
					}
				}).catch(() => {
					let _this = this
					Dialog.alert({
						title: '提示',
						message: '登录失效，重新登录',
					}).then(() => {
						_this.$router.push({path:'/login'});
					});
				})
			},
			// 反馈
			feedback () {
				if (window.getSelection().toString()) {
					Dialog.confirm({
						title: '提示',
						message: '是否需要对选中的内容反馈建议？',
						cancelButtonColorb: '#9C9D9E',
						confirmButtonColor: '#0067FF'
					}).then(() => {
						this.$router.push({path:'/feedbacks',query:{'content':window.getSelection().toString(),'reposId':this.id,'sectionId':this.sectionId}})
					})
				} else {
					Dialog.alert({
						title: '提示',
						message: '请长按选择需要反馈的内容，再进行问题反馈',
						confirmButtonColor: '#0067FF'
					})
				}
			},
			// 弹窗
			menuBtn () {
				this.show = true
			},
			// 获取目录
			getMenu () {
				this.$post('front/getSectionList.do', {
					'reposId': this.id,
				}).then((e) => {
					if (e.result === 'ok') {
						if (e.data.length > 0) {
							e.data.forEach(item =>{
								this.menuList.push({'sectionId': item.sectionId,'sectionName': item.sectionName,'level': 1})
								if (item.childSectionList.length > 0) {
									item.childSectionList.forEach(item1 => {
										this.menuList.push({'sectionId': item1.sectionId,'sectionName': item1.sectionName,'level': 2})
									})
								}
							})
						}
						this.getData()
					} else {
						this.$toast('服务器异常，请联系管理员');
					}
				}).catch(() => {
					let _this = this
					Dialog.alert({
						title: '提示',
						message: '登录失效，重新登录',
					}).then(() => {
						_this.$router.push({path:'/login'});
					});
				})
			},
			// 获取章节内容
			getSectionContent (index) {
				this.menuActive = index
				this.sectionId = this.menuList[index].sectionId
				this.show = false
				this.getData()
			},
			// 根据书签获取章节内容
			getMarkSectionContent (index) {
				let id = this.bookmarkList[index].section_id
				let name = this.bookmarkList[index].mark_content
				if (name == this.title) {
					this.menuActive = 0
					this.sectionId = ''
					this.show = false
					this.getData()
				} else {
					this.menuList.forEach((item,index) => {
						if (id == item.sectionId) {
							this.menuActive = index
							this.sectionId = id
							this.show = false
							this.getData()
						}
					})
				}

			},
			// 切换章节
			tabClick() {
				if (this.tabActive == 0) {
					if(this.menuActive == 0) {
						this.$toast('没有更多章节了')
					} else {
						this.menuActive -= 1
						this.sectionId = this.menuList[this.menuActive].sectionId
						this.getData()
					}
				} else {
					if(this.menuActive == this.menuList.length-1 || this.menuList.length == 0) {
						this.$toast('没有更多章节了')
					} else {
						this.menuActive += 1
						this.sectionId = this.menuList[this.menuActive].sectionId
						this.getData()
					}
				}
			}
		},
		mounted() {
			window.scrollTo(0,0)
		}
	};
</script>
<style scoped>
    .hello{
        min-height: calc(100vh - 1px);
        padding-top: 1px;
        position: relative;
        background: #fff;
    }
    >>>.van-nav-bar__title{
        color: #333;
    }
    .van-icon-arrow-left:before{
        color: #333;
    }
    >>>.van-nav-bar .van-icon{
        color: #333;
    }
    .content{
        padding: 15px;
        padding-bottom: 46px;
    }
    .info-box{
        width: calc(100% - 30px);
        padding: 0 15px;
        position: absolute;
        background: #fff;
    }
    .title{
        text-align: left;
        color: #333;
        font-size: 16px;
        font-weight: 500;
        margin: 0;
        margin-top: 10px;
    }
    .operation{
        display: flex;
        justify-content: space-between;
        padding: 15px 0 10px;
        border-bottom: 1px solid #E5E5E5;
        align-items: center;
    }
    .operation>div:nth-child(2){
        text-align: right;
    }
    .operation>div:nth-child(1) span{
        font-size: 10px;
        /*color: #6844DA;*/
        padding: 4px 8px;
        /*background: #E8ECFF;*/
        border-radius: 3px;
    }
    .tab-zy1{
        background: #FDF4E7;
        color: #C09359;
    }
    .tab-zy2{
        background: #6844DA;
        color: #E8ECFF;
    }
    .tab-zy3{
        background: #0067FF;
        color: #ECF5FE;
    }
    .tab-zy4{
        background: #14AF74;
        color: #E9F9F3;
    }
    .operation div img{
        height: 20px;
        margin-left: 20px;
    }
    .detail-box{
        padding: 15px 0;
        padding-bottom: 0;
    }
    .info{
        text-align: left;
        color: #333;
        font-size: 14px;
        line-height: 24px;
    }
    .info img{
        max-width: 100%;
        margin: 10px auto;
        display: flex;
    }
    .menuContent{
        height: 70vh;
    }
    >>>.van-tabs__line{
        height: 0;
    }
    >>>.van-tab__text{
        width: 80%;
        display: inline-block;
        height: 34px;
        line-height: 34px;
        color: #333;
        font-size: 15px;
        border-radius: 5px;
    }
    >>>.van-tab--active span{
        color: #fff;
        background: #0067FF;
    }
    >>>.van-tabs__content{
        height: calc(100% - 44px);
        /*padding: 0 15px;*/
    }
    >>>.van-tabs--line{
        height: 100%;
    }
    .chapter,.section{
        border-bottom: 1px solid #E5E5E5;
        text-align: left;
        font-size: 15px;
        color: #333;
        padding: 15px 0;
        margin: 0 15px;
    }
    .chapter{
        padding-left: 15px;
    }
    .section{
        padding-left: 45px;
    }
    .book-title,.book-info{
        padding: 0 15px;
        color: #333;
        font-size: 14px;
        text-align: left;
    }
    .book-title{
        background: #F5F5FA;
        font-weight: 500;
        padding: 5px 15px;
    }
    .book-info{
        overflow:hidden;
        text-overflow:ellipsis;
        display:-webkit-box;
        -webkit-box-orient:vertical;
        -webkit-line-clamp:2;
        line-height: 24px;
        margin: 15px;
    }
    .menuActive{
        color: #0067FF!important;
    }
    @media screen and (min-width: 1025px) {
        .hello {
            width: 80%;
            margin: 0 auto;
        }
        .van-tabbar,.van-popup--bottom{
            width: 80%;
            left: 10%;
        }
    }
</style>
