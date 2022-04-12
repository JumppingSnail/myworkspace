<template>
    <div class="hello">
        <van-nav-bar
                :fixed="true"
                left-arrow
                @click-left="onClickLeft"
                :title="title"
        >
            <template #right>
                <van-icon @click="showKeyWords" name="filter-o" size="18"/>
            </template>
        </van-nav-bar>

        <van-tabs @click="onClick" v-model="tabActive" sticky>
            <van-tab
                    v-for="(item, index) in navList"
                    :key="index"
                    :title="item.cat_name"
            >
                <van-list
                        v-model="Loading"
                        direction="down"
                        :finished="finished"
                        finished-text=""
                        @load="getCommendKnowledgeList"
                >
                    <div v-if="commendKnowledgeList.length > 0">
                        <div
                                class="detail-box"
                                v-for="(item, index) in commendKnowledgeList"
                                :key="index"
                                @click="articleDetail(index)"
                        >
                            <p class="detail-title">{{ item.cat_name + '：' + item.repos_title }}</p>
                            <p class="detail-info">{{ item.repos_digest }}</p>
                            <div class="keyword_name">
                                <span v-for="(item1,index1) in item.keyword_name" :key="index1">{{ item1 }}</span>
                            </div>
                        </div>
                    </div>
                    <div v-else>
                        <van-empty v-if="Loading" description="没有相关文章" />
                    </div>
                </van-list>
            </van-tab>
        </van-tabs>
        <van-popup v-model="show" position="right" :title="title" closeable>
            <div class="menuContent">
                <p>关键词</p>
                <div class="keyword_name" style="padding: 0 10px;">
                    <span style="padding:5px 10px;font-size: 12px;" :class="item1.active ? 'active' : ''" v-for="(item1,index1) in this.keywordList" :key="index1" @click="checkKeyWord(index1)">{{ item1.keyword_name }}</span>
                </div>
                <van-button class="login-btn" type="default" @click="submitBtn">确定</van-button>
            </div>
        </van-popup>
    </div>
</template>

<script>
	import { Dialog } from "vant";

	export default {
		data() {
			return {
				is_weixin: this.is_weixin(),
				keyWord: this.$route.query.keyWord ? this.$route.query.keyWord : "",
				navList: [
					{
						cat_name: "全部",
					},
				],
				finished: false,
				commendKnowledgeList: [],
				commendPage: 1,
				catagoryId: this.$route.query.catagoryId ? this.$route.query.catagoryId : "",
				title: this.$route.query.subjectName ? this.$route.query.subjectName : "",
				subjectId: this.$route.query.id ? this.$route.query.id : "",
				limit: 10,
				tabActive: 0,
				classArr: [],
				Loading: false,
				source: this.$route.query.source ? this.$route.query.source : 0,
				keywordIdList: [],
				keywordList: [],
				show: false
			};
		},
		created() {
			// 获取标签数据
			this.getNavList();
			this.getkeywordList();
			if (this.is_weixin) {
				document.title = "";
			}
		},
		methods: {
			//登录
			onClickLeft() {
				this.$router.push({ path: "/scientific" });
			},
			// 获取菜单列表
			getNavList() {
				this.$post("front/allCatagorylistForTop.do").then((e) => {
                    if (e.result === "ok") {
                        // 把最后一个“更多”去掉
                        if (e.data.length > 0 && !e.data[e.data.length - 1].id) {
                            e.data.pop();
                        }

                        e.data.forEach((item, index) => {
                            if (index % 4 == 0) {
                                this.classArr.push({ name: item.cat_name, class: "tab-zy1" });
                            } else if (index % 4 == 1) {
                                this.classArr.push({ name: item.cat_name, class: "tab-zy2" });
                            } else if (index % 4 == 2) {
                                this.classArr.push({ name: item.cat_name, class: "tab-zy3" });
                            } else if (index % 4 == 3) {
                                this.classArr.push({ name: item.cat_name, class: "tab-zy4" });
                            }
                        });
                        e.data.forEach((item) => {
                            this.navList.push(item);
                        });
                        let that = this;
                        this.$nextTick(function () {
                            that.tabActive = that.$route.query.tabActive ? Number(that.$route.query.tabActive) + 1 : 0;
                        });
                    } else {
                        this.$toast("服务器异常，请联系管理员");
                    }
                })
                .catch(() => {
                    let _this = this;
                    Dialog.alert({
                        title: "提示",
                        message: "登录失效，重新登录",
                    }).then(() => {
                        _this.$router.push({ path: "/login" });
                    });
                });
			},
			// 获取列表
			getCommendKnowledgeList() {
				this.$post("front/subjectDetailList.do", {
                    subjectId: this.subjectId,
                    catagoryIdSearch: this.catagoryId,
                    page: this.commendPage,
                    limit: this.limit,
                    keywordIdList: this.keywordIdList,
                }).then((e) => {
                    if (e.result === "ok") {
                        this.commendPage += 1;

                        e.data.list.forEach((item) => {
                            item.keyword_name = item.keyword_name.split('；')
                            this.commendKnowledgeList.push(item);
                        });

                        if (this.commendKnowledgeList.length >= parseInt(e.data.count)) {
                            this.finished = true;
                        }

                        this.Loading = false;
                    } else {
                        this.$toast("服务器异常，请联系管理员");
                    }
                }).catch(() => {
                    let _this = this;
                    Dialog.alert({
                        title: "提示",
                        message: "登录失效，重新登录",
                    }).then(() => {
                        _this.$router.push({ path: "/login" });
                    });
                });
			},
			onClick(index) {
				this.Loading = false;
				this.commendPage = 1;
				this.finished = false;
				this.catagoryId = this.navList[index].id || "";
				this.commendKnowledgeList = [];

				// 当切换“全部”时，此处不能自动触发获取列表方法
				// if (index == 0) {
				// 	this.getCommendKnowledgeList();
				// }
			},
			// 调整文章详情
			articleDetail(index) {
				let cat_name = this.commendKnowledgeList[index].cat_name;
				let id = this.commendKnowledgeList[index].id;
				if (cat_name == "书籍") {
					this.$router.push({
						path: "/book",
						query: { id: id, source: this.tabActive, type: 1 },
					});
				} else if (cat_name == "医案") {
					this.$router.push({
						path: "/record",
						query: { id: id, source: this.tabActive, type: 1 },
					});
				} else {
					this.$router.push({
						path: "/other",
						query: { id: id, source: this.tabActive, type: 1 },
					});
				}
			},
			// 弹窗
			showKeyWords () {
				this.show = true
			},
            // 获取关键词列表
            getkeywordList() {
                this.$post("front/allKeywordList.do", {
                    subjectId: this.subjectId,
                }).then((e) => {
                    if (e.result === "ok") {
                        e.data.forEach((item) => {
                            item.active = false
                            this.keywordList.push(item);
                        });
                    } else {
                        this.$toast("服务器异常，请联系管理员");
                    }
                })
                .catch(() => {
                    let _this = this;
                    Dialog.alert({
                        title: "提示",
                        message: "登录失效，重新登录",
                    }).then(() => {
                        _this.$router.push({ path: "/login" });
                    });
                });
            },
            // 关键词搜索
			submitBtn() {
				this.keywordIdList = []
				this.keywordList.forEach((item) => {
					if(item.active){
						this.keywordIdList.push(item.id)
                    }
				})
				this.show = false
				this.commendPage = 1
				this.finished = false
				this.commendKnowledgeList = []
				// this.getCommendKnowledgeList()
            },
            // 选中关键词
			checkKeyWord(index){
                if(!this.keywordList[index].active){
                    this.keywordList[index].active = true
                }else{
                    this.keywordList[index].active = false
                }
            }
		},
	};
</script>
<style scoped>
    .hello {
        min-height: 100vh;
        background: #fff;
        padding-top: 46px;
    }
    >>> .van-nav-bar--fixed {
        z-index: 11;
    }
    >>> .van-nav-bar__title {
        color: #333;
    }
    .van-search {
        padding: 0 15px;
        position: relative;
        z-index: 1;
        height: 40px;
        background: none;
        margin-top: 10px;
    }
    >>> .van-field__left-icon {
        position: absolute;
        right: 12px;
        margin-right: 0;
    }
    .van-icon-arrow-left:before {
        color: #333;
    }
    >>> .van-nav-bar .van-icon {
        color: #333;
    }
    >>> .van-field__left-icon .van-icon {
        z-index: 1;
    }
    >>> .van-tabs__line {
        background-color: #0067ff;
    }
    .detail-box {
        margin: 10px 15px;
        background: #fff;
        text-align: left;
        padding: 16px 0;
        padding-bottom: 6px;
        border-bottom: 1px solid #e5e5e5;
    }
    .detail-box p {
        margin: 0;
    }
    .detail-title {
        font-size: 16px;
        font-weight: 600;
    }
    .detail-info {
        font-size: 13px;
        color: #333;
        line-height: 20px;
        margin: 12px 0 !important;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
    }
    .tab-zy {
        font-size: 10px;
        text-align: center;
        background: #fdf4e7;
        color: #c09359;
        border-radius: 3px;
        padding: 4px 8px;
    }
    .tab-zy1 {
        background: #fdf4e7;
        color: #c09359;
    }
    .tab-zy2 {
        background: #6844da;
        color: #e8ecff;
    }
    .tab-zy3 {
        background: #0067ff;
        color: #ecf5fe;
    }
    .tab-zy4 {
        background: #14af74;
        color: #e9f9f3;
    }
    .keyword_name{
        display: flex;
        flex-wrap: wrap;
    }
    .keyword_name span{
        padding: 2px 6px;
        display: inline-block;
        color: #666;
        border: 1px solid #e5e5e5;
        font-size: 10px;
        border-radius: 2px;
        margin-right: 10px;
        margin-bottom: 10px;
    }
    .menuContent{
        /* height: 70vh; */
        height: 100vh;
        width: 70vw;
    }
    .login-btn{
        background: #0067FF;
        color: #fff;
        width: 70%;
        margin: 30px 15% 0;
        border-radius: 5px;
        font-size: 16px;
    }
    .active{
        background: #0067FF;
        border-color: #0067FF!important;
        color: #fff!important;
    }
    @media screen and (min-width: 1025px) {
        .hello {
            width: 80%;
            margin: 0 auto;
        }
    }
</style>
