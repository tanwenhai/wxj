new Vue($.extend(true, defaultVueOptions, {
  el: '#app',
  data: {
    userForm: {
      name: '',
      password: '',
      roleIdList: [],
    },
    roles: [],
    rules: {
      name: [
        { required: true, message: '请输入用户名称', trigger: 'blur' },
        { max: 32, message: '长度不能超过32个字符', trigger: 'blur' }
      ],
      password: [
        { required: true, message: '请输入密码', trigger: 'blur' }
      ]
    }
  },
  mounted: function () {
    this.initRole();
  },
  methods: {
    add: function (formName) {
      var self = this;
      this.$refs[formName].validate(function (valid) {
        if (!valid) {
          return false;
        }

        $.ajax({
          'url': '/api/user/add',
          'type': 'POST',
          'dataType': 'json',
          'contentType': 'application/json',
          'data': JSON.stringify(self.userForm)
        }).done(function (rsp) {
          if (rsp.status) {
            self.$message({
              type: 'success',
              message: '添加成功!',
              onClose: function () {
                location.pathname = '/admin/permission/user';
              }
            });
          } else {
            alert(rsp.message);
          }
        });

        return false;
      })
    },
    resetForm: function (formName) {
      this.$refs[formName].resetFields();
    },
    initRole: function () {
      var self = this;
      $.ajax({
        'url': '/api/role/search',
        'type': 'GET',
        'dataType': 'json'
      }).done(function (rsp) {
        var data = [];
        if (rsp.status) {
          data = rsp.value.list;
        }
        self.roles = data;
      });
    }
  }
}));