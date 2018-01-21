var queryString = qs(location.search);
var ue;

function validateUrl (rule, value, callback) {
  if (/^https?:\/\/.+$/.test(value)) {
    callback();
  }
  callback(new Error('请输入正确的URL'));
}
new Vue($.extend(true, defaultVueOptions, {
  el: '#app',
  data: {
    form: {
      id: queryString.id || '',
      name: '',
      imgUrl: '',
      content: ''
    },
    btnText: queryString.id ? '修改' : '添加',
    rules: {
      name: [
        { required: true, message: '请输入店铺名称', trigger: 'blur' },
      ],
      imgUrl: [
        { required: true, message: '请输入图片地址', trigger: 'blur' },
        { validator: validateUrl, trigger: 'change' }
      ]
    }
  },
  mounted: function () {
    var self = this;
    ue = UE.getEditor('container');
    if (this.form.id) {
      ue.addListener( 'ready', function() {
        self.getDetail();
      });
    }
  },
  methods: {
    getShops: getShops,
    add: function (formName) {
      var self = this;
      this.$refs[formName].validate(function (valid) {
        if (!valid) {
          return false;
        }
        self.form.content = ue.getContent();
        var url = self.form.id ? '/api/shop/update' : '/api/shop/add';
        $.ajax({
          'url': url,
          'type': 'POST',
          'dataType': 'json',
          'contentType': 'application/json',
          'data': JSON.stringify(self.form)
        }).done(function (rsp) {
          if (rsp.status) {
            self.$message({
              type: 'success',
              message: rsp.value,
              onClose: function () {
                location.pathname = '/admin/shop/index';
              }
            });
          } else {
            alert(rsp.message);
          }
        });

        return false;
      }.bind(this))
    },
    getDetail: getDetail,
    resetForm: function (formName) {
      this.$refs[formName].resetFields();
    }
  }
}));

function getDetail() {
  $.ajax({
    url: '/api/shop/detail',
    type: 'GET',
    data: {
      id: this.form.id
    },
    dataType: 'JSON',
  }).done(function (rsp) {
    if (rsp.status) {
      rsp.value.shopList = $.map(rsp.value.shopList, function (v) {
        return v.id;
      });
      this.form = rsp.value;
      ue.setContent(rsp.value.content);
    }
  }.bind(this))
}

function getShops() {
  var self = this;
  $.ajax({
    'url': '/api/shop/search',
    'type': 'GET',
    'data': {
      'pageNo': 1,
      'pageSize': 999
    },
    'dataType': 'json'
  }).done(function (rsp) {
    var data = [];
    if (rsp.status) {
      data = rsp.value.list;
    }
    self.shopList = data;
  });
}
