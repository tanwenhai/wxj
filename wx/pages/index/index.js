const config = require('../../config.js');
//获取应用实例
const app = getApp()

Page({
  data: {
    categorys: [],
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onReady: function (e) {
    wx.request({
      url: `${config.service.host}/api/category/search`,
      data: {
        pageNo: 1,
        pageSize: 999
      },
      success: (rsp) => {
        if (rsp.statusCode === 200 && rsp.data.status) {
          let data = rsp.data;
          this.setData({
            categorys: data.value.list
          });
        }
      }
    });
  },
  onLoad: function (options) {
    var that = this
    //获取系统信息  
    wx.getSystemInfo({
      //获取系统信息成功，将系统窗口的宽高赋给页面的宽高  
      success: function (res) {
        that.width = res.windowWidth
        // console.log(that.width)   375
        that.height = res.windowHeight
        // console.log(that.height)  625
        // 这里的单位是PX，实际的手机屏幕有一个Dpr，这里选择iphone，默认Dpr是2
      }
    })
  }
})
