<template>
    <div class="hello">
        <van-nav-bar :fixed=true left-arrow @click-left="onClickLeft" title="我的反馈" />
        <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="getCommendKnowledgeList">
            <div class="detail-box" v-for="(item,index) in commendKnowledgeList" :key="index">
                <p class="detail-title" @click="articleDetail(index)">{{ '【' + item.cat_name +'】' + item.repos_title + item.select_content }}</p>
                <p class="detail-info">{{ item.feedback_content }}</p>
                <p class="detail-time">{{ item.feedback_time.substring(0,19).replace('T',' ') }}</p>
                <p v-if="item.deal_state == 3" class="detail-result"><img src="../../../static/img/feedback.png" style="height: 14px;margin-right: 10px;" alt="">已处理：{{ item.deal_user_name }}</p>
                <p v-else-if="item.deal_state == 2" class="detail-result"><img src="../../../static/img/feedback.png" style="height: 14px;margin-right: 10px;" alt="">跟进中</p>
                <p v-else class="detail-result"><img src="../../../static/img/feedback.png" style="height: 14px;margin-right: 10px;" alt="">未处理</p>
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
			};
		},
        created() {
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
				this.$post('front/myFeedbackList.do', {
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
			// 调整文章详情
			articleDetail (index) {
				let cat_name = this.commendKnowledgeList[index].cat_name
				let id = this.commendKnowledgeList[index].repos_id
				let sectionId = this.commendKnowledgeList[index].section_id
				if (cat_name == '书籍') {
					this.$router.push({path: '/book', query: { 'id': id,'sectionId':sectionId }})
				} else {
					this.$router.push({path: '/other', query: { 'id': id,'sectionId':sectionId }})
				}
			}
		},
	};
</script>
<style scoped>
    .hello{
        min-height: 100vh;
        padding-top: 46px;
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
        padding: 10px 15px;
    }
    .detail-box p{
        margin: 0;
    }
    .detail-title{
        font-size: 16px;
        font-weight: 600;
        overflow:hidden;
        text-overflow:ellipsis;
        white-space: nowrap;
        background: #F5F5FA;
        padding: 6px 0;
        border-radius: 3px;
        cursor: pointer;
    }
    .detail-info{
        font-size: 13px;
        color: #333;
        line-height: 20px;
        margin: 12px 0!important;
        overflow:hidden;
        text-overflow:ellipsis;
        white-space: nowrap;
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
    .detail-time{
        color: #666;
        font-size: 12px;
    }
    .detail-result{
        color: #0067FF;
        font-size: 13px;
        margin-top: 15px!important;
        align-items: center;
        display: flex;
    }
    @media screen and (min-width: 1025px) {
        .hello {
            width: 80%;
            margin: 0 auto;
        }
    }
</style>
