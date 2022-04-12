<template>
    <div class="hello">
        <van-nav-bar left-arrow :border=false @click-left="onClickLeft" title="培训详情" />
        <div class="content-box">
            <div class="title">
                <div>{{ this.data.train_subject }}</div>
                <div>进行中</div>
            </div>
            <div class="address">培训地址：{{ this.data.train_place }}</div>
            <div class="address">培训时间：{{ this.data.start_time.substring(0,19).replace('T',' ') }}</div>
            <div class="detail-box" v-html="data.train_content"></div>
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
                id: this.$route.query.id,
                data: {}
			};
		},
		created() {
			// 获取数据
			this.getData()
			if (this.is_weixin) {
				document.title = ''
			}
		},
		methods: {
			onClickLeft() {
				this.$router.go(-1)
			},
            getData () {
				this.$post('front/myTrainDetail.do', {
					'trainId': this.id
				}).then((e) => {
					if (e.result === 'ok') {
						this.data = e.data
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
                    })
				})
            }
		},
	};
</script>
<style scoped>
    .hello{
        min-height: 100vh;
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
    .content-box{
        padding: 17px 15px;
    }
    .title{
        display: flex;
        align-items: center;
    }
    .title div:nth-child(1){
        flex: 1;
        text-align: left;
        font-size: 16px;
        color: #333;
        font-weight: 500;
    }
    .title div:nth-child(2){
        color: #fff;
        font-size: 12px;
        background: #0067FF;
        padding: 3px 7px;
        border-radius: 3px;
    }
    .address{
        font-size: 14px;
        color: #666;
        text-align: left;
        margin: 12px 0;
    }
    .detail-box{
        padding: 15px 0;
        border-top: 1px solid #E5E5E5;
        text-align: left;
        line-height: 24px;
        padding-bottom: 0;
    }
    >>>.detail-box img{
        max-width: 100%;
        margin: 10px auto 0;
        display: flex;
    }
    @media screen and (min-width: 1025px) {
        .hello {
            width: 80%;
            margin: 0 auto;
        }
    }
</style>
