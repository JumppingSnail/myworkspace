<template>
    <div class="hello">
        <van-nav-bar :fixed=true left-arrow @click-left="onClickLeft" title="详情" />
        <div class="info-box">
            <p class="title">{{ title }}</p>
            <div class="operation">
                <div>
                    <div v-for="(site,index) in classArr" :key="index">
                        <span v-if="cat_name == site.name" class="tab-zy" :class="cat_name == site.name ? site.class : ''">{{ cat_name }}</span>
                    </div>
                </div>
            </div>
            <div class="operation">
                <table cellspacing="1">
                    <tr>
                        <td>姓名</td>
                        <td>{{ info.patient_name }}</td>
                        <td>年龄</td>
                        <td>{{ info.patient_age }}</td>
                    </tr>
                    <tr>
                        <td>出处</td>
                        <td colspan="3">{{ info.repos_title }}</td>
                    </tr>
                    <tr>
                        <td>章节</td>
                        <td colspan="3">{{ info.section_name }}</td>
                    </tr>
                </table>
            </div>
            <div class="diagnosis">
                <p class="title">诊断</p>
                <div>
                    <span>中医诊断</span>
                    <span>{{ data.cnDiseaseMap }}</span>
                </div>
                <div>
                    <span>病因主诉</span>
                    <span>{{ info.case_description }}</span>
                </div>
            </div>
            <div>
                <p class="title">处方记录</p>
                <div class="diagnosis" :style="index!==dataList.length - 1?'border-bottom:1px solid #e2e2e2;':''" v-for="(item,index) in dataList" :key="index">
                    <div>
                        <span>治疗类型</span>
                        <span>{{ item.change_content }}</span>
                    </div>
                    <div>
                        <span>方剂名称</span>
                        <span>{{ item.treat_name }}</span>
                    </div>
                    <div>
                        <span>方剂明细</span>
                        <span>{{ item.drug_info }}</span>
                    </div>
                    <div>
                        <span>用量</span>
                        <span>{{ item.treat_usage }}</span>
                    </div>
                    <div>
                        <span>治疗效果</span>
                        <span>{{ item.treat_result }}</span>
                    </div>
                </div>
            </div>
        </div>
        <van-tabbar>
            <van-tabbar-item style="color: #fff;background: #0067FF" @click="tabClick">阅读原文</van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<script>

	import {Dialog} from "vant";

	export default {
		data() {
			return {
				is_weixin: this.is_weixin(),
				title: '',
				id: this.$route.query.id,
				data: {},
                info: {},
				collect_flag: '0',
				cat_name: '医案',
				dataList: [],
				classArr: [],
				source: this.$route.query.source?this.$route.query.source:'',
				section_id: '',
				repos_id: '',
				catagoryId: '',
				indexActive: this.$route.query.active?this.$route.query.active:'',
				keyWord: this.$route.query.keyWord?this.$route.query.keyWord:'',
				type: this.$route.query.type?this.$route.query.type:''
			};
		},
		created() {
			// 获取数据
			this.getDetail()
			this.getNavList()
		},
		methods: {
			//登录
			onClickLeft() {
				if (this.source) {
					if(this.type){
						if (this.source > 0) {
							this.$router.push({path: '/diseaseSearch', query: { 'catagoryId': this.catagoryId, 'tabActive': this.source - 1}})
						} else {
							this.$router.push({path: '/diseaseSearch'})
						}
                    }else{
						if (this.source > 0) {
							this.$router.push({path: '/search', query: { 'catagoryId': this.catagoryId, 'tabActive': this.source - 1, 'keyWord': this.keyWord, 'source': 1}})
						} else {
							this.$router.push({path: '/search', query: {'keyWord': this.keyWord}})
						}
                    }
				} else if (this.indexActive) {
					this.$router.push({path: '/',query: {'active': this.indexActive}})
				} else {
					this.$router.go(-1)
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
			getDetail () {
				this.$post('front/getMedicalCaseInfo.do', {
					'caseId': this.id
				}).then((e) => {
					if (e.result === 'ok') {
						if (this.is_weixin) {
							document.title = ''
						} else {
							document.title = '详情'
						}
						this.title = e.data.cnDiseaseMap + '|' + e.data.info.patient_name
						this.catagoryId = e.data.info.cat_id
                        this.info = e.data.info
                        this.data = e.data
                        this.dataList = e.data.treatmentList
                        this.repos_id = e.data.info.repos_id
                        this.section_id = e.data.info.section_id
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
			tabClick() {
				this.$router.push({path: '/book', query: { 'id': this.repos_id,'sectionId':this.section_id }})
			}
		},
	};
</script>
<style scoped>
    .hello{
        min-height: 100vh;
        padding: 50px 0;
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
    .content{
        padding: 15px;
    }
    .info-box{
        width: calc(100% - 30px);
        padding: 0 15px;
        /*position: fixed;*/
        background: #fff;
    }
    .title{
        text-align: left;
        color: #333;
        font-size: 16px;
        font-weight: 500;
        margin: 0;
    }
    .title:nth-child(1){
        margin-top: 10px;
    }
    .operation{
        display: flex;
        justify-content: space-between;
        padding: 15px 0 10px;
        align-items: center;
    }
    .operation>div:nth-child(2){
        text-align: right;
    }
    .operation>div:nth-child(1) span{
        font-size: 10px;
        /*color: #6844DA;*/
        padding: 4px 8px;
        /*background: #E8ECFF;*/
        border-radius: 3px;
    }
    .operation:nth-child(3){
        padding-top: 0;
    }
    .operation table{
        width: 100%;
        background: #999;
    }
    .operation tr td:nth-child(1),.operation tr td:nth-child(3){
        width: 35px;
        text-align: center;
    }
    .operation td{
        text-align: left;
        background: #fff;
        color: #666;
        padding: 5px;
        font-size: 14px;
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
    .operation div img{
        height: 20px;
        margin-left: 20px;
    }
    .detail-box{
        /*padding: 15px 0;*/
        padding-bottom: 0;
    }
    .info{
        text-align: left;
        color: #333;
        font-size: 14px;
        line-height: 24px;
    }
    .info img{
        max-width: 100%;
        margin: 10px auto;
        display: flex;
    }
    .diagnosis div{
        display: flex;
        justify-content: space-between;
    }
    .diagnosis div span:nth-child(1){
        width: 75px;
        text-align: left;
        font-size: 14px;
        color: #666;
        padding: 5px 0;
    }
    .diagnosis div span:nth-child(2){
        text-align: left;
        flex: 1;
        font-size: 14px;
        color: #666;
        padding: 5px 0;
    }
    @media screen and (min-width: 1025px) {
        .hello {
            width: 80%;
            margin: 0 auto;
        }
    }
</style>
