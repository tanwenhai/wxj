new Vue($.extend(true, defaultVueOptions, {
  el: '#app',
  watch: {
    filterText: function (val) {
      this.$refs.tree2.filter(val);
    }
  },
  mounted: function () {
    this.search();
  },
  methods: {
    filterNode: function (value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    search: function () {
      var self = this;
      $.ajax({
        'url': '/api/permission/tree',
        'type': 'GET',
        'dataType': 'json'
      }).done(function (rsp) {
        var data = [];
        if (rsp.status) {
          data = rsp.value;
        }
        self.permissions = data;
      });
    }
  },
  data: {
    filterText: '',
    permissions: [],
    defaultProps: {
      children: 'children',
      label: 'name'
    }
  }
}));