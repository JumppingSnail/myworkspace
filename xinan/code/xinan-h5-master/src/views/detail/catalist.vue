<template>
  <div class="hello">
    <van-nav-bar title="栏目列表" />
    <van-list v-model="loading" :finished="finished" @load="getNavList">
      <div
        class="detail-box"
        v-for="(item, index) in navList"
        :key="index"
        @click="goToDeatil(index)"
      >
        <div class="img">
          <img :src="url + item.cat_icon_url" alt="" />
        </div>
        <div class="info">
          <p>栏目名称：{{ item.cat_name }}</p>
          <p>栏目简介：{{ item.cat_description }}</p>
        </div>
      </div>
    </van-list>
  </div>
</template>

<script>
import { Dialog } from "vant";

export default {
  data() {
    return {
      url: this.url,
      loading: false,
      finished: false,
      navList: [],
      is_weixin: this.is_weixin(),
    };
  },
  created() {
    if (this.is_weixin) {
      document.title = "";
    }
  },
  methods: {
    //底部跳转
    onChange(index) {
      if (index === 0) {
        this.$router.push({ path: "/" });
      } else if (index === 2) {
        this.$router.push({ path: "/user" });
      }
    },
    getNavList() {
      this.$post("front/allCatagorylist.do")
        .then((e) => {
          if (e.result === "ok") {
            // 把最后一个“更多”去掉
            if (e.data.length > 0 && !e.data[e.data.length - 1].id) {
              e.data.pop();
            }

            e.data.forEach((item) => {
              this.navList.push(item);
            });

            this.finished = true;
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

    // 跳转详情
    goToDeatil(index) {
      if (this.navList[index].cat_name == "医案") {
        this.$router.push({
          path: "/search",
          query: {
            catagoryId: this.navList[index].id,
            tabActive: index,
            source: 1,
          },
        });
      } else {
        this.$router.push({
          path: "/search",
          query: { catagoryId: this.navList[index].id, tabActive: index },
        });
      }
    },
  },
};
</script>
<style scoped>
.hello {
  min-height: 100vh;
  padding-top: 1px;
}
.detail-box {
  display: flex;
  margin: 0 15px;
  margin-top: 10px;
  justify-content: space-between;
  padding: 15px 10px;
  background: #fff;
  border-radius: 10px;
}
.img {
  position: relative;
}
.img img {
  width: 70px;
  height: 70px;
}
.img span {
  position: absolute;
  bottom: 5px;
  right: 0;
  background: #0067ff;
  color: #fff;
  text-align: center;
  border-radius: 3px;
  display: inline-block;
  font-size: 10px;
  padding: 2.5px 5px;
}
.info {
  flex: 1;
  padding-left: 10px;
  padding-top: 8px;
}
.info p {
  margin: 0;
  text-align: left;
  color: #666;
  font-size: 14px;
}
.info p:nth-child(1) {
  color: #333;
  font-size: 15px;
}
.end {
  color: #999 !important;
  background: #fff !important;
}
@media screen and (min-width: 1025px) {
  .hello {
    width: 80%;
    margin: 0 auto;
  }
  .van-tabbar--fixed {
    width: 80%;
    left: 10%;
  }
}
</style>
