<template>
    <div class="hello">
        <div class="mask"></div>
        <van-nav-bar
                title="首页"
                :border=false
        />
        <van-search @search="keyWordSearch" v-model="keyWord" shape="round" placeholder="药品、疾病、方剂、典籍等搜索" :clearable=false>
            <template #left-icon>
                <van-icon name="search" @click="keyWordSearch" />
            </template>
        </van-search>
        <div class="nav-list">
            <van-grid :column-num="4" :border="false">
                <van-grid-item v-for="(item,index) in navList" :key="index" :text="item.cat_name" @click="navDetail(index)">
                    <template #icon>
                        <img style="height: 44px;" :src="url+item.cat_icon_url" alt="" v-if="item.cat_icon_url">
                    </template>
                </van-grid-item>
            </van-grid>
        </div>
        <van-tabs v-model="tabActive" line-height="3" line-width="15" title-active-color="#0067FF">
            <van-tab title="推荐">
                <van-list v-model="loading" :finished="finished" finished-text="" @load="getCommendKnowledgeList">
                    <div class="detail-box" v-for="(item,index) in commendKnowledgeList" :key="index" @click="articleDetail(index,'commendKnowledgeList')">
                        <p class="detail-title">{{ item.repos_title }}</p>
                        <p class="detail-info">{{ item.repos_digest }}</p>
                        <div v-for="(site,index) in classArr" :key="index">
                            <span v-if="item.cat_name == site.name" class="tab-zy" :class="item.cat_name == site.name ? site.class : ''">{{ item.cat_name }}</span>
                        </div>
                    </div>
                </van-list>
            </van-tab>
            <van-tab title="最新">
                <van-list v-model="newLoading" :finished="newFinished" finished-text="" @load="getNewKnowledgeList">
                    <div class="detail-box" v-for="(item,index) in newKnowledgeList" :key="index" @click="articleDetail(index,'newKnowledgeList')">
                        <p class="detail-title">{{ item.repos_title }}</p>
                        <p class="detail-info">{{ item.repos_digest }}</p>
                        <div v-for="(site,index) in classArr" :key="index">
                            <span v-if="item.cat_name == site.name" class="tab-zy" :class="item.cat_name == site.name ? site.class : ''">{{ item.cat_name }}</span>
                        </div>
                    </div>
                </van-list>
            </van-tab>
        </van-tabs>
        <van-tabbar v-model="active" @change="onChange">
            <van-tabbar-item>
                <span>首页</span>
                <template #icon>
                    <img src="../../../static/img/nav_shouye_pre@2x(1).png" />
                </template>
            </van-tabbar-item>
            <van-tabbar-item>
                <span>培训通知</span>
                <template #icon>
                    <img src="../../../static/img/nav_notice.png" />
                </template>
            </van-tabbar-item>
            <van-tabbar-item>
                <span>消渴肾病</span>
                <template #icon>
                    <img src="../../../static/img/scientific.png" />
                </template>
            </van-tabbar-item>
            <van-tabbar-item>
                <span>我的</span>
                <template #icon>
                    <img src="../../../static/img/nav_wode@2x.png" />
                </template>
            </van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<script>
	import { Dialog } from 'vant';
	export default {
		data() {
			return {
				active: 0,
				tabActive: this.$route.query.active?Number(this.$route.query.active):0,
				icon: {
					active: 'https://img01.yzcdn.cn/vant/user-active.png',
					inactive: 'https://img01.yzcdn.cn/vant/user-inactive.png',
				},
				keyWord: '',
                navList: [],
                url: this.url,
				commendKnowledgeList: [],
				commendPage: 1,
				newKnowledgeList: [],
				newPage: 1,
				limit: 5,
				loading: false,
				finished: false,
				newLoading: false,
				newFinished: false,
                is_weixin: this.is_weixin(),
				classArr: []
			};
		},
        created() {
			// 获取数据
			this.getNavList()
            if (this.is_weixin) {
                document.title = ''
            }
		},
		methods: {
			//底部跳转
			onChange(index) {
				if (index === 1) {
					this.$router.push({path:'/notice'});
                } else if (index === 3) {
					this.$router.push({path:'/user'});
                } else if (index === 2){
					this.$router.push({path:'/scientific'});
                }
			},
            //监听搜索
			keyWordSearch() {
				if (this.keyWord) {
					this.$router.push({path: '/search', query: { 'keyWord': this.keyWord }})
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
                        this.navList = e.data

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
            // 获取推荐列表
			getCommendKnowledgeList () {
				this.$post('front/commendKnowledgeList.do', {
					'catagoryIdSearch': '',
					'page': this.commendPage,
                    'limit': this.limit
				}).then((e) => {
					this.loading = false
					if (e.result === 'ok') {
						this.commendPage += 1
						if (e.data.length < 5) {
							this.finished = true
                        }
                        e.data.forEach(item =>{
                            this.commendKnowledgeList.push(item)
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
            // 获取最新列表
			getNewKnowledgeList () {
				this.$post('front/newKnowledgeList.do', {
					'catagoryIdSearch': '',
					'page': this.newPage,
					'limit': this.limit
				}).then((e) => {
					this.newLoading = false
					if (e.result === 'ok') {
						this.newPage += 1
						if (e.data.length < 5) {
							this.newFinished = true
						}
						e.data.forEach(item =>{
							this.newKnowledgeList.push(item)
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
            // 分类列表跳转
			navDetail (index) {
				if (this.navList[index].cat_name == '医案') {
                    this.$router.push({path: '/search', query: { 'catagoryId': this.navList[index].id, 'tabActive': index, 'source': 1 }})
                } else if(!this.navList[index].id){ // 此处为系统默认添加的“更多”
                    this.$router.push({path: '/catalist', query: {}})
                } else {
					this.$router.push({path: '/search', query: { 'catagoryId': this.navList[index].id, 'tabActive': index }})
				}
			},
			// 调整文章详情
			articleDetail (index,type) {
                let cat_name = ''
                let id = ''
                if (type == 'commendKnowledgeList') {
                    cat_name = this.commendKnowledgeList[index].cat_name
                    id = this.commendKnowledgeList[index].id
                } else {
                    cat_name = this.newKnowledgeList[index].cat_name
                    id = this.newKnowledgeList[index].id
                }
                if (cat_name == '书籍') {
                    this.$router.push({path: '/book', query: { 'id': id, 'active': this.tabActive }})
                } else if (cat_name == '医案') {
                    this.$router.push({path: '/record', query: { 'id': id, 'active': this.tabActive }})
                } else {
                    this.$router.push({path: '/other', query: { 'id': id, 'active': this.tabActive }})
                }
            }
		},
	};
</script>
<style scoped>
    .hello{
        min-height: 100vh;
        padding-bottom: 50px;
    }
    .mask{
        width: 100%;
        height: 35%;
        position: absolute;
        top: 0;
        left: 0;
        background-image: linear-gradient(#006DFF, #F5F5FA);
    }
    .van-nav-bar{
        background: none;
    }
    >>>.van-nav-bar__title{
        color: #fff;
    }
    >>>.van-nav-bar__text{
        color: #fff;
        font-size: 20px;
    }
    .van-search__content{
        background: #fff;
    }
    .van-search{
        padding: 0 15px;
        position: relative;
        z-index: 1;
        height: 40px;
        background: none;
    }
    >>>.van-field__left-icon{
        position: absolute;
        right: 12px;
        margin-right: 0;
    }
    .nav-list{
        padding: 10px 15px;
    }
    .van-grid{
        padding: 10px;
        background: #fff;
        border-radius: 8px;
        z-index: 1;
        position: relative;
    }
    >>>.van-tabs__nav{
        background: none;
    }
    >>>.van-tabs__line{
        background-color: #0067FF;
    }
    .detail-box{
        margin: 10px 15px;
        background: #fff;
        border-radius: 8px;
        text-align: left;
        padding: 10px 15px;
    }
    .detail-box p{
        margin: 0;
    }
    .detail-title{
        font-size: 16px;
        font-weight: 600;
    }
    .detail-info{
        font-size: 13px;
        color: #333;
        line-height: 20px;
        margin: 12px 0!important;
        overflow:hidden;
        text-overflow:ellipsis;
        display:-webkit-box;
        -webkit-box-orient:vertical;
        -webkit-line-clamp:2;
    }
    .tab-zy{
        font-size: 10px;
        text-align: center;
        background: #FDF4E7;
        color: #C09359;
        border-radius: 3px;
        padding: 4px 8px;
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
    >>>.van-field__left-icon .van-icon{
        z-index: 1;
    }
    @media screen and (min-width: 1025px) {
        .hello {
            width: 80%;
            margin: 0 auto;
        }
        .van-tabbar--fixed{
            width: 80%;
            left: 10%;
        }
    }
</style>
