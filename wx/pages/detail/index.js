const WxParse = require('../../vendor/wxParse/wxParse.js');
const config = require('../../config.js');

// pages/nav2/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    shops: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    
    let id = options.id;
    wx.request({
      url: `${config.service.host}/api/shop/detail`,
      data: {
        id
      },
      success: (rsp) => {
        if (rsp.statusCode === 200 && rsp.data.status) {
          let data = rsp.data;
          WxParse.wxParse('article', 'html', data.value.content, that, 5);
        }
      }
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (options) {
    return {
      title: '要出国游的人注意了……',
      path: '/pages/detail/index?id=123',
      success: function (res) {
        // 转发成功
        alert('转发成功')
      },
      fail: function (res) {
        // 转发失败
        alert('转发成功')
      }
    }
  }
})