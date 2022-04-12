<template>
  <div class="hello">
    <van-nav-bar
      :fixed="true"
      left-arrow
      @click-left="onClickLeft"
      title="搜索"
    />
    <van-search
      @search="keyWordSearch"
      v-model="keyWord"
      shape="round"
      placeholder="药品、疾病、方剂、典籍等搜索"
      :clearable="false"
    >
      <template #left-icon>
        <van-icon name="search" @click="keyWordSearch" />
      </template>
    </van-search>
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
              <p class="detail-title">{{ item.repos_title }}</p>
              <p class="detail-info">{{ item.repos_digest }}</p>
              <div v-for="(site, index) in classArr" :key="index">
                <span
                  v-if="item.cat_name == site.name"
                  class="tab-zy"
                  :class="item.cat_name == site.name ? site.class : ''"
                  >{{ item.cat_name }}</span
                >
              </div>
            </div>
          </div>
          <div v-else>
            <van-empty v-if="Loading" description="没有相关文章" />
          </div>
        </van-list>
      </van-tab>
    </van-tabs>
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
      catagoryId: this.$route.query.catagoryId
        ? this.$route.query.catagoryId
        : "",
      limit: 10,
      tabActive: 0,
      classArr: [],
      Loading: false,
      source: this.$route.query.source ? this.$route.query.source : 0,
    };
  },
  created() {
    // 获取标签数据
    this.getNavList();
    if (this.is_weixin) {
      document.title = "";
    }
  },
  methods: {
    //登录
    onClickLeft() {
      this.$router.push({ path: "/" });
    },
    //监听搜索
    keyWordSearch() {
      this.commendPage = 1;
      this.finished = false;
      this.commendKnowledgeList = [];
      // this.getCommendKnowledgeList()
    },
    // 获取菜单列表
    getNavList() {
      this.$post("front/allCatagorylist.do")
        .then((e) => {
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
              that.tabActive = that.$route.query.tabActive
                ? Number(that.$route.query.tabActive) + 1
                : 0;
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
      let url = "";
      if (this.navList[this.tabActive].remark || this.source) {
        this.source = 0;
        url = "front/getMedicalCaseListByPhone.do";
      } else {
        url = "front/fullSearchList.do";
      }
      this.$post(url, {
        catagoryIdSearch: this.catagoryId,
        fullContentSearch: this.keyWord,
        page: this.commendPage,
        limit: this.limit,
      })
        .then((e) => {
          if (e.result === "ok") {
            this.commendPage += 1;

            e.data.list.forEach((item) => {
              this.commendKnowledgeList.push(item);
            });

            if (this.commendKnowledgeList.length >= parseInt(e.data.count)) {
              this.finished = true;
            }

            this.Loading = false;
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
    onClick(index) {
      this.Loading = false;
      this.commendPage = 1;
      this.finished = false;
      this.catagoryId = this.navList[index].id || "";
      this.commendKnowledgeList = [];

      // 当切换“全部”时，此处不能自动触发获取列表方法
      // if (index == 0) {
      //   this.getCommendKnowledgeList();
      // }
    },
    // 调整文章详情
    articleDetail(index) {
      let cat_name = this.commendKnowledgeList[index].cat_name;
      let id = this.commendKnowledgeList[index].id;
      if (cat_name == "书籍") {
        this.$router.push({
          path: "/book",
          query: { id: id, source: this.tabActive, keyWord: this.keyWord },
        });
      } else if (cat_name == "医案") {
        this.$router.push({
          path: "/record",
          query: { id: id, source: this.tabActive, keyWord: this.keyWord },
        });
      } else {
        this.$router.push({
          path: "/other",
          query: { id: id, source: this.tabActive, keyWord: this.keyWord },
        });
      }
    },
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
@media screen and (min-width: 1025px) {
  .hello {
    width: 80%;
    margin: 0 auto;
  }
}
</style>
