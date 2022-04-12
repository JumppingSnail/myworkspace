<template>
    <div class="hello">
        <van-nav-bar :fixed=true left-arrow :border=false @click-left="onClickLeft" title="修改密码" />
        <div class="content">
            <p class="title">修改密码</p>
            <p class="wjmm">首次登陆，请设置新密码</p>
            <van-field v-model="oldPassword" type="password" clearable placeholder="请输入原密码">

            </van-field>

            <van-field v-model="newPassword" type="password" clearable placeholder="请输入新密码">

            </van-field>
            <van-button class="login-btn" type="default" @click="login">确认修改</van-button>
        </div>
    </div>
</template>

<script>
	import {Dialog} from "vant";

	export default {
		data() {
			return {
				oldPassword: '',
				newPassword: '',
				is_weixin: this.is_weixin(),
                message: '登录失效，重新登录'
			};
		},
		created() {
			if (this.is_weixin) {
				document.title = ''
			}
		},
		methods: {
			//登录
			login() {
				if (!this.oldPassword || !this.newPassword) {
					this.$toast('密码不能为空')
				} else {
					this.$post('front/changePassword.do', {
						'oldPassword': this.oldPassword,
						'newPassword': this.newPassword
					}).then((e) => {
                        if (e.result == 'weak') {
                            this.$toast('密码强度不够，要求最少8位，并且至少包括大小写字母、数字、字符其中3种组合！')
                        } else if (e.result == '314') {
                            this.$toast('原密码不正确！')
                        } else if (e.result == 'ok') {
                            this.message = '修改成功，重新登录'
                            this.$cookie.remove()
                            Dialog.alert({
                                title: '提示',
                                message: this.message
                            }).then(() => {
                                this.$router.push({path:'/login'})
                            });
                        } else {
                            this.$toast('服务器内部错误！')
                        }
					}).catch(() => {
						Dialog.alert({
							title: '提示',
							message: this.message
						}).then(() => {
							this.$router.push({path:'/login'})
						});
					})
				}
			},
			onClickLeft() {
				this.$router.go(-1)
            }
		},
	};
</script>
<style scoped>
    .hello{
        min-height: 100vh;
        background: #fff;
    }
    .content{
        padding: 81px 25px 0;
    }
    .title{
        font-size: 33px;
        color: #333;
        text-align: left;
        margin: 0;
        margin-bottom: 70px;
    }
    .van-cell {
        padding: 10px 0;
        border-bottom: 1px solid #EEEEEE;
    }
    .input-img{
        height: 24px;
    }
    .van-cell::after{
        display: none;
    }
    .wjmm{
        text-align: left;
        font-size: 15px;
        margin: 16px 0;
        color: #333;
    }
    .login-btn{
        background: #0067FF;
        color: #fff;
        width: 100%;
        border-radius: 5px;
        font-size: 16px;
        margin-top: 60px;
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
    @media screen and (min-width: 1025px) {
        .hello {
            width: 80%;
            margin: 0 auto;
        }
    }
</style>
