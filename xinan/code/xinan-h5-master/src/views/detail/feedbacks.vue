<template>
    <div class="hello">
        <van-nav-bar left-arrow :border=false @click-left="onClickLeft" title="反馈意见" />
        <div class="content">
            <p>[{{ cat_name }}]《{{ title }}》{{ content }}</p>
            <textarea v-model="memo" class="feedContent" placeholder="输入您的建议，200字" maxlength="200"></textarea>
            <van-button class="login-btn" type="default" @click="submitBtn">提交</van-button>
        </div>
    </div>
</template>

<script>

	import {Dialog} from "vant";

	export default {
		data() {
			return {
				is_weixin: this.is_weixin(),
				id: this.$route.query.reposId,
				cat_name: '',
				sectionId: this.$route.query.sectionId?this.$route.query.sectionId:'',
				chapter_name: '',
				title: '',
                content: this.$route.query.content,
                memo: ''
			};
		},
		created() {
			// 获取数据
			this.getDetail()
			if (this.is_weixin) {
				document.title = ''
			}
		},
		methods: {
			onClickLeft() {
				this.$router.go(-1)
			},
			getDetail () {
				this.$post('front/fetchKnowledgeMainInfo.do', {
					'reposId': this.id
				}).then((e) => {
					if (e.result === 'ok') {
						this.title = e.data.repos_title
						this.cat_name = e.data.cat_name
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
			submitBtn () {
				if (!this.memo) {
					this.$toast('反馈内容不能为空！')
                    return
                }
				this.$post('front/saveMyFeedback.do', {
					'reposId': this.id,
					'sectionId': this.sectionId,
					'feedbackContent': this.memo,
					'selectContent': this.content
				}).then((e) => {
					if (e.result === 'ok') {
						Dialog.alert({
							title: '提示',
							message: '反馈成功',
							confirmButtonColor: '#0067FF'
						}).then(() => {
							if (this.cat_name == '书籍') {
								this.$router.push({path: '/book', query: { 'id': this.id,'sectionId':this.sectionId }})
							} else {
								this.$router.push({path: '/other', query: { 'id': this.id,'sectionId':this.sectionId }})
							}
						});
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
        background: #fff;
    }
    .content{
        padding: 15px;
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
    .content p{
        margin: 0;
        font-size: 14px;
        color: #333;
        text-align: left;
    }
    .feedContent{
        padding: 11px;
        height: 200px;
        background: #F5F5FA;
        margin: 15px 0;
        width: calc(100% - 30px);
        border: none;
        font-size: 14px;
        color: #999;
        border-radius: 5px;
    }
    .login-btn{
        background: #0067FF;
        color: #fff;
        width: 100%;
        font-size: 16px;
        margin-top: 15px;
        border-radius: 22px;
    }
    @media screen and (min-width: 1025px) {
        .hello {
            width: 80%;
            margin: 0 auto;
        }
    }
</style>
