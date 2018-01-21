var queryString = qs(location.search);
new Vue($.extend(true, defaultVueOptions, {
  el: '#app',
  data: {
    form: {
      id: queryString.id || '',
      name: '',
      shopList: [],
    },
    tag: '',
    filterText: '',
    inputVisible: false,
    btnText: queryString.id ? '修改' : '添加',
    shopList: [],
    rules: {
      name: [
        { required: true, message: '请输入城市名称', trigger: 'blur' },
      ]
    }
  },
  mounted: function () {
    this.getShops();
    if (this.form.id) {
      this.getDetail();
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
        var url = self.form.id ? '/api/city/update' : '/api/city/add';
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
                location.pathname = '/admin/city/index';
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
    },
    handleClose: function (tag) {
      this.form.citys.splice(this.form.citys.indexOf(tag), 1);
    },

    showInput: function () {
      this.inputVisible = true;
      this.$nextTick(function (_) {
        this.$refs.saveTagInput.$refs.input.focus();
      }.bind(this));
    },
    handleInputConfirm: function () {
      var tag = this.tag;
      if (tag && this.form.citys.indexOf(tag) == -1) {
        this.form.citys.push(tag);
      }
      this.inputVisible = false;
      this.tag = '';
    }
  }
}));

function getDetail() {
  $.ajax({
    url: '/api/city/detail',
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
