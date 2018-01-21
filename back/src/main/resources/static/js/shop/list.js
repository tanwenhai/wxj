var methods = {
  search: function () {
    var self = this;
    $.ajax({
      'url': '/api/shop/search',
      'type': 'GET',
      'data': {
        'name': this.name,
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
  changePageSize: function (pageSize) {
    this.pageSize = pageSize;
  },
  changeCurrentPage: function (currentPage) {
    this.currentPage = currentPage;
  },
  delNav: function (id) {
    var self = this;
    this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(function () {
      $.ajax({
        url: '/api/category/del',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
          id: id
        }),
        dataType: 'JSON'
      }).done(function (rsp) {
        if (rsp.status) {
          self.$message({
            type: 'success',
            message: '删除成功!',
            onClose: function () {
              self.search();
            }
          });
        }
      });
    }).catch(function () {
      self.$message({
        type: 'info',
        message: '已取消删除'
      });
    });
  }
};

var queryString = qs(location.search);
new Vue($.extend(true, defaultVueOptions, {
  el: '#app',
  data: {
    name: '',
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
  filters: {
    dateFormat: function (value) {
      return moment(new Date(value)).format('YYYY-MM-DD HH:mm:ss');
    },
    statusConvert: function (value) {
      return {
        '10': '正常'
      }[value] || '无效的状态值';
    }
  },
  watch: {
    pageSize: methods.search,
    currentPage: methods.search
  }
}));
