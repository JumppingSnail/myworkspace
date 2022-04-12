<template>
    <div class="hello">
        <van-nav-bar title="个人中心"/>
        <div class="user-box">
            <img src="../../../static/img/user.png" alt="">
            <div>
                <p>{{name}}</p>
                <p>{{type}}</p>
            </div>
        </div>
        <van-cell title="我的收藏" is-link style="margin-top: 10px;" @click="myCollection">
            <template #icon>
                <img style="height: 20px;margin-right: 10px;margin-top: 1px;" src="../../../static/img/shoucang.png" alt="">
            </template>
        </van-cell>
        <van-cell title="我的反馈" is-link @click="myFeedback">
            <template #icon>
                <img style="height: 20px;margin-right: 10px;margin-top: 1px;" src="../../../static/img/fankui.png" alt="">
            </template>
        </van-cell>
        <van-cell title="修改密码" is-link style="margin-top: 10px;" @click="savePassword">
            <template #icon>
                <img style="height: 20px;margin-right: 10px;margin-top: 1px;" src="../../../static/img/mima.png" alt="">
            </template>
        </van-cell>
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
                    <img src="../../../static/img/scientific.png" />
                </template>
            </van-tabbar-item>
            <van-tabbar-item>
                <span>我的</span>
                <template #icon>
                    <img src="../../../static/img/nav_wode_pre@2x.png" />
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
				active: 3,
				tabActive: 0,
				icon: {
					active: 'https://img01.yzcdn.cn/vant/user-active.png',
					inactive: 'https://img01.yzcdn.cn/vant/user-inactive.png',
				},
				name: '',
                type: '',
				is_weixin: this.is_weixin()
			};
		},
		created() {
			// 获取数据
			this.getUserInfo()
			if (this.is_weixin) {
				document.title = ''
			}
		},
        methods: {
			//底部跳转
			onChange(index) {
				if (index === 0) {
					this.$router.push({path:'/'})
				} else if (index === 1) {
					this.$router.push({path:'/notice'})
				} else if (index === 2){
					this.$router.push({path:'/scientific'})
				}
			},
			// 修改密码
			savePassword() {
				this.$router.push({path:'/password'})
            },
            // 我的收藏
			myCollection() {
				this.$router.push({path:'/collection'})
            },
            // 我的反馈
			myFeedback() {
				this.$router.push({path:'/feedback'});
            },
            // 获取用户信息
			getUserInfo() {
				this.$post('front/fetchCurrentUserInfo.do').then((e) => {
					if (e.result === 'ok') {
						this.name = e.data.realName
						this.type = e.data.userType === 3 ? '医生' : '其他'
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
            }
		},
	};
</script>
<style scoped>
    .hello{
        min-height: 100vh;
    }
    .user-box{
        padding: 15px;
        display: flex;
        justify-content: space-between;
        background: #fff;
    }
    .user-box img{
        width: 58px;
        height: 58px;
        border-radius: 50%;
    }
    .user-box div{
        padding-left: 15px;
        padding-top: 8px;
        flex: 1;
        text-align: left;
    }
    .user-box div p{
        margin: 0;
    }
    .user-box div p:nth-child(1){
        color: #333333;
        font-size: 18px;
    }
    .user-box div p:nth-child(2){
        color: #7F7F7F;
        font-size: 14px;
    }
    .van-cell__title{
        text-align: left;
    }
    .van-cell{
        padding: 16px;
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
