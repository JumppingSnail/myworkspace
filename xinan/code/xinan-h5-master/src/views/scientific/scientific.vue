<template>
    <div class="hello">
        <van-nav-bar title="消渴病肾病"/>
        <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="getMyTrainList">
            <div class="detail-box"  v-for="(item,index) in myTrainList" :key="index" @click="goToDeatil(item.id,item.subject_name)">
                <div class="img">
                    <img :src="url + item.image_url" alt="">
                </div>
                <div class="info">
                    {{ item.subject_name }}
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
                    <img src="../../../static/img/nav_notice.png" />
                </template>
            </van-tabbar-item>
            <van-tabbar-item>
                <span>消渴肾病</span>
                <template #icon>
                    <img src="../../../static/img/scientific-1.png" />
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
				active: 2,
				tabActive: 0,
				icon: {
					active: 'https://img01.yzcdn.cn/vant/user-active.png',
					inactive: 'https://img01.yzcdn.cn/vant/user-inactive.png',
				},
				url: this.url,
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
				} else if (index === 1) {
					this.$router.push({path:'/notice'})
				} else if (index === 3){
					this.$router.push({path:'/user'});
				}
			},
			getMyTrainList() {
				this.$post('front/allSubjectList.do', {
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
			goToDeatil (id,subjectName) {
				this.$router.push({path: '/diseaseSearch', query: { 'id': id,subjectName: subjectName }})
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

    .img img{
        width: 70px;
        height: 70px;
    }

    .info{
        flex: 1;
        display: flex;
        text-align: left;
        align-items: center;
        padding-left: 10px;
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
