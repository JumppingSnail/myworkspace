<template>
    <div class="hello">
        <van-nav-bar title="登录验证" />
        <div class="content">
            <p class="title">登录</p>
            <van-field v-model="username" clearable placeholder="请输入手机号">
                <template  #left-icon>
                    <img class="input-img" src="../../../static/img/icon_shoujihao@2x.png" />
                </template>
            </van-field>

            <van-field v-model="password" type="password" clearable placeholder="请输入密码">
                <template  #left-icon>
                    <img class="input-img" src="../../../static/img/icon_mima@2x.png" />
                </template>
            </van-field>
            <p class="wjmm">忘记密码，请联系管理员</p>
            <van-button class="login-btn" type="default" @click="login">验证登录</van-button>
        </div>
    </div>
</template>

<script>
	export default {
		data() {
			return {
				username: '',
				password: '',
				is_weixin: this.is_weixin()
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
				if (!this.username || !this.password) {
					this.$toast('账号密码不能为空');
				} else {
					this.$post('front/login.do', {
						'userCode': this.username,
						'password': this.password
					}).then((e) => {
						if (e.result == '301' || e.result == '302') {
							this.$toast('用户名或密码不正确！')
						} else if (e.result == '304') {
							this.$toast('密码重试次数过多，该用户被锁定30分钟！')
                        } else if (e.result == '313') {
							this.$toast('该用户已经被停用！')
						} else if (e.result == 'error') {
							this.$toast('服务器内部错误！')
						} else {
							this.$cookie.set('token',e.data)
							this.$router.push({path: '/'})
                        }
					}).catch(() => {
						this.$toast('登录失败，请重试!');
					})
				}
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
        padding: 25px 25px 0;
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
        text-align: right;
        font-size: 15px;
        margin: 16px 0;
        color: #0067FF;
    }
    .login-btn{
        background: #0067FF;
        color: #fff;
        width: 100%;
        border-radius: 5px;
        font-size: 16px;
    }
    @media screen and (min-width: 1025px) {
        .hello {
            width: 40%;
            margin: 0 auto;
        }
    }
</style>
