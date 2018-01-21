var methods = {
  search: function () {
    var self = this;
    $.ajax({
      'url': '/api/auditLog/search',
      'type': 'GET',
      'data': {
        'username': this.username,
        'pageNo': this.pageNo,
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
  }
};


new Vue($.extend(true, defaultVueOptions, {
  el: '#app',
  data: {
    username: '',
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
    },
    typeConvert: function (value) {
      return {
        '10': '查询',
        '20': '新增',
        '30': '更新',
        '40': '删除',
      }[value] || '无效的状态值';
    }
  },
}));