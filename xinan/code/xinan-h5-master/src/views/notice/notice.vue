<template>
    <div class="hello">
        <van-nav-bar title="培训通知"/>
        <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="getMyTrainList">
            <div class="detail-box"  v-for="(item,index) in myTrainList" :key="index" @click="goToDeatil(item.id)">
                <div class="img">
                    <img src="../../../static/img/notice.png" alt="">
                    <span :class="item.train_state === '3' ? 'end' : ''">{{ item.train_state === '3' ? '已结束' : '进行中'}}</span>
                </div>
                    <div class="info">
                        <p>{{ item.train_subject }}</p>
                        <p>培训地址：{{ item.train_place }}</p>
                        <p>培训时间：{{ item.start_time.substring(0,19).replace('T',' ') }}</p>
                    </div>
            </div>
        </van-list>
        <van-tabbar v-model="active" @change="onChange">
            <van-tabbar-item>
                <span>首页</span>
                <template #icon>
                    <img src="../../../static/img/nav_shouye_pre@2x.png" />
                </template>
            </van-tabbar-item>
            <van-tabbar-item>
                <span>培训通知</span>
                <template #icon>
                    <img src="../../../static/img/nav_haowu@2x(1).png" />
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
	import {Dialog} from "vant";

	export default {
		data() {
			return {
				active: 1,
				tabActive: 0,
				icon: {
					active: 'https://img01.yzcdn.cn/vant/user-active.png',
					inactive: 'https://img01.yzcdn.cn/vant/user-inactive.png',
				},
				page: 1,
				limit: 5,
				loading: false,
				finished: false,
				myTrainList: [],
				is_weixin: this.is_weixin()
			};
		},
		created() {
			if (this.is_weixin) {
				document.title = ''
			}
		},
		methods: {
			//底部跳转
			onChange(index) {
				if (index === 0) {
					this.$router.push({path:'/'});
				} else if (index === 3) {
					this.$router.push({path:'/user'})
				} else if (index === 2){
					this.$router.push({path:'/scientific'});
				}
			},
            getMyTrainList() {
				this.$post('front/myTrainList.do', {
					'page': this.page,
                    'limit': this.limit
				}).then((e) => {
					this.loading = false
                    if (e.result === 'ok') {
                        this.page += 1
                        if (e.data.length < 5) {
                            this.finished = true
                        }
                        e.data.forEach(item =>{
                            this.myTrainList.push(item)
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
            // 跳转详情
			goToDeatil (id) {
				this.$router.push({path: '/noticeDetail', query: { 'id': id }})
			}
		},
	};
</script>
<style scoped>
    .hello{
        min-height: 100vh;
        padding-top: 1px;
    }
    .detail-box{
        display: flex;
        margin: 0 15px;
        margin-top: 10px;
        justify-content: space-between;
        padding: 15px 10px;
        background: #fff;
        border-radius: 10px;
    }
    .img{
        position: relative;
    }
    .img img{
        width: 70px;
        height: 70px;
    }
    .img span{
        position: absolute;
        bottom: 5px;
        right: 0;
        background: #0067FF;
        color: #fff;
        text-align: center;
        border-radius: 3px;
        display: inline-block;
        font-size: 10px;
        padding: 2.5px 5px
    }
    .info{
        flex: 1;
        padding-left: 10px;
        padding-top: 8px;
    }
    .info p{
        margin: 0;
        text-align: left;
        color: #666;
        font-size: 14px;
    }
    .info p:nth-child(1){
        color: #333;
        font-size: 15px;
    }
    .end{
        color: #999!important;
        background: #fff!important;
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
