<template>
    <div class="hello">
        <van-nav-bar left-arrow @click-left="onClickLeft" title="我的收藏" />
        <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="getCommendKnowledgeList">
            <div class="detail-box" v-for="(item,index) in commendKnowledgeList" :key="index" @click="articleDetail(index)">
                <p class="detail-title">{{ item.repos_title }}</p>
                <p class="detail-info">{{ item.repos_digest }}</p>
                <div v-for="(site,index) in classArr" :key="index">
                    <span v-if="item.cat_name == site.name" class="tab-zy" :class="item.cat_name == site.name ? site.class : ''">{{ item.cat_name }}</span>
                </div>
            </div>
        </van-list>
    </div>
</template>

<script>
	import {Dialog} from "vant";

	export default {
		data() {
			return {
				is_weixin: this.is_weixin(),
				loading: false,
				finished: false,
				commendKnowledgeList: [],
				commendPage: 1,
				limit: 5,
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
			//登录
			onClickLeft() {
				this.$router.go(-1)
			},
			// 获取列表
			getCommendKnowledgeList () {
				this.$post('front/myCollectList.do', {
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
			// 调整文章详情
			articleDetail (index) {
				let cat_name = this.commendKnowledgeList[index].cat_name
				let id = this.commendKnowledgeList[index].repos_id
				if (cat_name == '书籍') {
					this.$router.push({path: '/book', query: { 'id': id }})
				} else {
					this.$router.push({path: '/other', query: { 'id': id }})
				}
			}
		},
	};
</script>
<style scoped>
    .hello{
        min-height: 100vh;
        padding-top: 1px;
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
    .detail-box{
        margin: 10px 15px;
        background: #fff;
        border-radius: 8px;
        text-align: left;
        padding: 15px 10px;
        cursor: pointer;
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
        background: #FDF4E7;
        width: 36px;
        height: 18px;
        line-height: 18px;
        display: inline-block;
        color: #C09359;
        font-size: 10px;
        text-align: center;
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
    @media screen and (min-width: 1025px) {
        .hello {
            width: 80%;
            margin: 0 auto;
        }
    }
</style>
