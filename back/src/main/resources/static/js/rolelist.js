var methods = {
  search: function () {
    var self = this;
    $.ajax({
      'url': '/api/role/search',
      'type': 'GET',
      'data': {
        'rolename': this.rolename,
        'username': this.username,
        'pageNo': this.currentPage,
        'pageSize': this.pageSize
      },
      'dataType': 'json'
    }).done(function (rsp) {
      var data = [];
      if (rsp.status) {
        data = rsp.value.list;
        self.total = rsp.value.total;
        self.currentPage = rsp.value.pageNum;
        self.pageSize = rsp.value.pageSize;
      }
      self.tableData = data;
    });
  },
  showRole: function(id) {
    console.log(id);
  },
  delRole: function (id) {
    var self = this;
    this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(function () {
      $.ajax({
        'url': '/api/role/del',
        'type': 'POST',
        'data': JSON.stringify({
          'id': id
        }),
        'contentType': 'application/json',
        'dataType': 'json'
      }).done(function (rsp) {
        if (rsp.status) {
          self.$message({
            type: 'success',
            message: '删除成功!',
            onClose: function () {
              self.search();
            }
          });
        } else {
          self.$message({
            type: 'error',
            message: rsp.message
          })
        }
      });
    }).catch(function () {
      self.$message({
        type: 'info',
        message: '已取消删除'
      });
    });
  },
  changePageSize: function (pageSize) {
    this.pageSize = pageSize;
  },
  changeCurrentPage: function (currentPage) {
    this.currentPage = currentPage;
  }
};

var queryString = qs(location.search);
new Vue($.extend(true, defaultVueOptions, {
  el: '#app',
  data: {
    rolename: '',
    username: queryString.username || '',
    tableData: [],
    total: 0,
    currentPage: 1,
    pageSizes: [1, 2, 3, 30],
    pageSize: 1
  },
  mounted: function () {
    this.search();
  },
  methods: methods,
  watch: {
    pageSize: methods.search,
    currentPage: methods.search
  },
  filters: {
    dateFormat: function (value) {
      return moment(new Date(value)).format('YYYY-MM-DD HH:mm:ss');
    }
  },
}));