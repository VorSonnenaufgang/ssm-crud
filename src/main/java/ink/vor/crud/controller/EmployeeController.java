package ink.vor.crud.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ink.vor.crud.pojo.Employee;
import ink.vor.crud.pojo.Msg;
import ink.vor.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理员工CRUD请求
 *
 * @author muquanrui
 * @date 16/02/2022 17:40
 */

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * 导入jackson包
     *
     * @param pn
     * @return com.github.pagehelper.PageInfo
     */
    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        // 包装查询后的结果，将pageInfo交给页面
        // 封装了详细的分页信息
        PageInfo pageInfo = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }

    /**
     * 查询员工数据（分页查询）
     *
     * @return java.lang.String
     */
    // @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        // 包装查询后的结果，将pageInfo交给页面
        // 封装了详细的分页信息
        PageInfo pageInfo = new PageInfo(emps, 5);
        model.addAttribute("pageInfo", pageInfo);
        return "list";
    }

    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(Employee employee) {
        employeeService.saveEmp(employee);
        return Msg.success();
    }

    @RequestMapping("/checkuser")
    @ResponseBody
    public Msg checkuser(String empName) {
        boolean b = employeeService.checkUser(empName);
        if (b) {
            return Msg.success();
        } else {
            return Msg.fail();
        }
    }

    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id) {
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp", employee);
    }

    /**
     * 如果直接用AJAX发PUT请求
     * 请求体中有数据
     * 但是Employee封装不上
     *
     *
     * 原因：
     * 	 * Tomcat：
     * 	 * 		1、将请求体中的数据，封装一个map。
     * 	 * 		2、request.getParameter("empName")就会从这个map中取值。
     * 	 * 		3、SpringMVC封装POJO对象的时候。
     * 	 * 				会把POJO中每个属性的值，request.getParamter("email");
     * 	 * AJAX发送PUT请求引发的血案：
     * 	 * 		PUT请求，请求体中的数据，request.getParameter("empName")拿不到
     * 	 * 		Tomcat一看是PUT不会封装请求体中的数据为map，只有POST形式的请求才封装请求体为map
     * 	 * org.apache.catalina.connector.Request--parseParameters() (3111);
     * 	 *
     * 	 * protected String parseBodyMethods = "POST";
     * 	 * if( !getConnector().isParseBodyMethod(getMethod()) ) {
     *                 success = true;
     *                 return;
     *             }
     * 	 *
     * 	 *
     * 	 * 解决方案；
     * 	 * 我们要能支持直接发送PUT之类的请求还要封装请求体中的数据
     * 	 * 1、配置上HttpPutFormContentFilter；
     * 	 * 2、他的作用；将请求体中的数据解析包装成一个map。
     * 	 * 3、request被重新包装，request.getParameter()被重写，就会从自己封装的map中取数据
     *
     * @param employee
     * @return ink.vor.crud.pojo.Msg
     */
    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    @ResponseBody
    public Msg updateEmp(Employee employee) {
        employeeService.updateEmp(employee);
        return Msg.success();
    }

    @RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Msg deleteEmpyById(@PathVariable("id") String ids) {
        if (ids.contains("-")) {
            String[] str_ids = ids.split("-");
            List<Integer> del_ids = new ArrayList<>();
            for (String str : str_ids) {
                del_ids.add(Integer.parseInt(str));
            }
            employeeService.deleteBatch(del_ids);
        } else {
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }
        return Msg.success();
    }
}
