<template>
    <div class="hello">
        <van-nav-bar :fixed=true left-arrow @click-left="onClickLeft" :title="title" />
        <div class="info-box" :style="is_weixin?'top:46px;':'top:46px;'">
            <p class="title">{{ title }}</p>
            <div class="operation">
                <div>
                    <div v-for="(site,index) in classArr" :key="index">
                        <span v-if="cat_name == site.name" class="tab-zy" :class="cat_name == site.name ? site.class : ''">{{ cat_name }}</span>
                    </div>
                </div>
                <div>
                    <img src="../../../static/img/icon_jubao@2x.png" @click="feedback" alt="">
                    <img v-if="collect_flag == '0'" src="../../../static/img/collection.png" alt="" @click="collection">
                    <img v-else src="../../../static/img/collection-check.png" alt=""  @click="collectionCancel">
                </div>
            </div>
        </div>
        <div class="content" :style="is_weixin?'padding-top: 137px;':'padding-top: 137px;'">
            <div class="detail-box">
                <div class="info">
                    {{ info }}
                </div>
                <div v-for="(item,index) in dataList" :key="index">
                    <p class="title">{{item.section_name}}</p>
                    <div class="info" v-html="item.section_content"></div>
                </div>
            </div>
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
				collect_flag: '0',
				cat_name: '',
                info: '',
                dataList: [],
				classArr: [],
				source: this.$route.query.source?this.$route.query.source:'',
				catagoryId: '',
                indexActive: this.$route.query.active?this.$route.query.active:'',
				keyWord: this.$route.query.keyWord?this.$route.query.keyWord:'',
				type: this.$route.query.type?this.$route.query.type:''
			};
		},
		created() {
			// 获取数据
			this.getDetail()
			this.getData()
			this.getNavList()
		},
		methods: {
			//登录
			onClickLeft() {
				if (this.source) {
					if(this.type){
						if (this.source > 0) {
							this.$router.push({path: '/diseaseSearch', query: { 'catagoryId': this.catagoryId, 'tabActive': this.source - 1}})
						} else {
							this.$router.push({path: '/diseaseSearch'})
						}
                    }else{
						if (this.source > 0) {
							this.$router.push({path: '/search', query: { 'catagoryId': this.catagoryId, 'tabActive': this.source - 1, 'keyWord': this.keyWord}})
						} else {
							this.$router.push({path: '/search', query: {'keyWord': this.keyWord}})
						}
                    }
				} else if (this.indexActive) {
					this.$router.push({path: '/',query: {'active': this.indexActive}})
                } else {
					this.$router.go(-1)
				}
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
						if (this.is_weixin) {
							document.title = ''
						} else {
							document.title = e.data.repos_title
						}
						this.title = e.data.repos_title
                        this.collect_flag = e.data.collect_flag
                        this.info = e.data.repos_digest
                        this.cat_name = e.data.cat_name
						this.catagoryId = e.data.cat_id
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
				this.$post('front/fetchKnowledgeContent.do', {
					'reposId': this.id,
					'sectionId': ''
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
						this.$router.push({path:'/feedbacks',query:{'content':window.getSelection().toString(),'reposId':this.id}})
					})
                } else {
					Dialog.alert({
						title: '提示',
						message: '请长按选择需要反馈的内容，再进行问题反馈',
						confirmButtonColor: '#0067FF'
					})
                }
            }
		},
	};
</script>
<style scoped>
    .hello{
        min-height: 100vh;
        padding-top: 1px;
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
    }
    .info-box{
        width: calc(100% - 30px);
        padding: 0 15px;
        position: fixed;
        background: #fff;
    }
    .title{
        text-align: left;
        color: #333;
        font-size: 16px;
        font-weight: 500;
        margin: 0;
    }
    .title:nth-child(1){
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
        /*padding: 15px 0;*/
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
    @media screen and (min-width: 1025px) {
        .hello {
            width: 80%;
            margin: 0 auto;
        }
    }
</style>
