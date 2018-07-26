package com.kevin.common.GlobalConstant;

public class GlobalConstant {

	public static final String PARAM_IS_EMPTY = "参数为空！";

	public static final String ACCOUNT_OR_PASSWORD_ERROR = "用户名或密码错误！";
	public static final String VALIDATECODE_ERROR = "验证码不正确！";
	public static final String VALIDATECODE_INVALID = "验证码已失效！";
	public static final String NO_ACCOUNT = "找不到该用户！";
	public static final String UNACTIVATED = "该用户已注册，请等待管理员审核……";

	public static final String CURR_USER = "currUser";// 当前用户
	public static final String CURR_USER_URL_LIST = "currUserUrlList";// 当前用户所能访问URL
	public static final String CURR_USER_ROLE_ID_LIST = "currUserRoleIdList";// 当前用户全部角色Id

	public static final String CURR_USER_ROLE_ID = "currUserRoleId";// 当前用户角色ID
	public static final String CURR_USER_ROLE_IDENTIFY = "currUserRoleIdentify";// 当前用户角色标识

	//重置密码
	public static final String RESET_PASSWORD = "123456";

	public static final String MENU_CATEGORY_ID_SYS = "sys";

	//******************  短信验证码 ！   ******************
	//验证码有效分钟
	public static final int VALI_CODE_MINUTE = 5;
	//短信验证码
	public static final String valiCode = "valiCode";
	//短信验证码失效时间
	public static final String valiCodeFailTime = "valiCodeFailTime";
	//参与手机号
	public static final String mobile = "mobile";

	public static final String VALI_CODE_SEND_SUCCESS = "验证码发送成功！";
	public static final String VALI_CODE_SEND_FAIL = "验证码发送失败！";

	public static final String VALI_CODE_ERROR = "验证码错误！";
	public static final String VALI_CODE_INVALID = "验证码失效！";

	public static final String VALI_PASS = "验证通过！";

	//******************  登录   ******************
	public static final String LOGIN_SUCCESS = "登录成功！";
	public static final String LOGIN_FAIL = "登录失败！";


	public static final String PHONE_EXIST = "手机号已存在！";
	public static final String REGISTER_OK = "注册成功";

	public static final String NULL_INITIALIZED = "未加载初始化数据！";
	public static final String NULL_MOBILE = "未获取到系统配置的手机号！";

	/**
	 * 角色标识
	 */
	// 数据管理员
	public static final String ROLEID_DATABASE_MANAGE = "roleId_database_manage";
	// 工艺路线审核人
	public static final String ROLE_ID_ROUTER_AUDITOR = "roleId_router_auditor";
	// 工艺设计师
	public static final String ROLEID_PROCESS_DESIGN = "roleId_process_design";
	// 工单审核人
	public static final String ROLE_ID_PIES_AUDITOR = "roleId_pies_auditor";
	// 研发计划审核人
	public static final String ROLE_ID_PLAN_AUDITOR = "roleId_plan_auditor";
	//管理员 ROLE_ID
	public static final String ROLE_ID_ADMIN = "roleId_admin";
	//项目负责人 ROLE_ID
	public static final String ROLE_ID_PROJ_LEADER = "roleId_proj_leader";
	//项目计划员 ROLE_ID
	public static final String ROLE_ID_PROJ_PLANNER = "roleId_proj_planner";
	//总计划员 ROLE_ID
	public static final String ROLE_ID_PLANNER = "roleId_planner";
	//生产计划员 ROLE_ID
	public static final String ROLE_ID_PRO_PLANNER = "roleId_pro_planner";
	//研发计划员 ROLE_ID
	public static final String ROLE_ID_DEV_PLANNER = "roleId_dev_planner";
	//研发批准 ROLE_ID
	public static final String ROLEID_RESEARCH_APPROVE = "roleId_research_approve";
	//研发审批(审核) ROLE_ID
	public static final String ROLEID_RESEARCH_AUDITOR = "roleId_research_auditor";
	//研发计划负责人 ROLE_ID
	/*public static final String ROLEID_DEV_LEADER = "roleId_dev_leader";*/
	//研发设计师  ROLE_ID
	public static final String ROLEID_RESEARCH_DESIGN = "roleId_research_design";
	//车间主管 ROLE_ID
	public static final String ROLE_ID_WORKSHOP_LEADER = "roleId_workshop_leader";
	//班组长 ROLE_ID
	public static final String ROLE_ID_TEAM_LEADER = "roleId_team_leader";
	//操作工 ROLE_ID
	public static final String ROLE_ID_OPERATOR = "roleId_operator";
	//检验员 ROLE_ID
	public static final String ROLE_ID_CHECKER = "roleId_checker";
	//物料配送员 ROLE_ID
	public static final String ROLE_ID_MATERIAL_DELIVER = "roleId_material_deliver";
	//设备管理员 ROLE_ID
	public static final String ROLE_ID_EQUIP_ADMIN = "roleId_equip_admin";
	//仓库管理员 ROLE_ID
	public static final String ROLE_ID_STORAGE_ADMIN = "roleId_storage_admin";
	//部门负责人 ROLE_ID
	public static final String ROLE_ID_DEPT_LEADER = "roleId_dept_leader";
	//问题负责人 ROLE_ID
	public static final String ROLE_ID_PROBLEM_LEADER = "roleId_problem_leader";
	//设计bom审核人 ROLE_ID
	public static final String ROLE_ID_DESIONBOM_AUDITOR = "roleId_designbom_auditor";
	//结果
	public static final String RESULT = "result";
	//消息
	public static final String MSG = "msg";

	//错误消息
	public static final String ERROR_MSG = "errorMsg";
	//异常消息
	public static final String EXCEPTION_MSG = "exceptionMsg";

	//******************  系统配置 ！   ******************
	/**
	 * 系统标题名称
	 */
	public static final String SYS_TITLE_NAME = "sys_title_name";

	/**
	 * 文件上传验证
	 */
	//允许上传 文件 的MIME类型
	public static final String ACCEPT_FILE_MIME = "accept_file_mime";
	//允许上传 文件 的后缀名
	public static final String ACCEPT_FILE_SUFFIX = "accept_file_suffix";

	//允许上传 image 的MIME类型
	public static final String ACCEPT_IMAGE_MIME = "accept_image_mime";
	//允许上传 image 的后缀名
	public static final String ACCEPT_IMAGE_SUFFIX = "accept_image_suffix";
	//允许上传image的大小限制(M)
	public static final String ACCEPT_IMAGE_SIZE_LIMIT = "accept_image_size_limit";
	//允许上传 Excel 的MIME类型
	public static final String ACCEPT_EXCEL_MIME = "accept_excel_mime";
	//允许上传 Excel 的后缀名
	public static final String ACCEPT_EXCEL_SUFFIX = "accept_excel_suffix";
	//允许上传文件的大小限制(M)
	public static final String ACCEPT_FILE_SIZE_LIMIT = "accept_file_size_limit";
	//上传文件保存物理路径
	public static final String UPLOAD_BASE_DIR = "upload_base_dir";

	//上传访问url根地址
	public static final String UPLOAD_BASE_URL = "upload_base_url";

	//上传图片缩略图的前缀 add by lixh 2016-9-27
	public static final String UPLOAD_IMG_THUMB_PREFIX = "thumb-";

	public static final int UPLOAD_IMG_THUMB_WIDTH = 100;

	public static final int UPLOAD_IMG_THUMB_HEIGHT = 100;


	//临时文件夹
	public static final String TEMP = "temp";




	//********************************  文件上传验证 ！   **********************************

	public static final String IMAGE_SIZE_EXCEED = "图片大小不能超过";
	public static final String FILE_SIZE_EXCEED = "文件大小不能超过";
	//存储单位  M
	public static final String FILE_UNIT_M = "M";
	//存储单位为1024进制。
	public static final long FILE_HEX = 1024;
	//存储单位1024 二次方：  1024*1024。
	public static final long FILE_HEX_TWO_POWER = 1048576;

	//******************   短信接口用户名、密码 ***************************
	public static String ENCODING = "UTF-8";
	public static  final String API_KEY = "b23a1d958ab15ee29ee8d9a5a37fd903";
	public static final String SHORTMESSAGE_SERVICE_ID ="17715257693";
	public static final String SHORTMESSAGE_SERVICE_PASSWD ="MSHOW123456";

	//******************   AJAX 返回 JSON 信息 ***************************

	public static final String PARAM_ERROR = "参数错误！";
	public static final String CODE_ERROR = "编号重复，请重新输入！";

	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";

	public static final String SAVE_SUCCESSED = "保存成功！";
	public static final String SAVE_FAIL = "保存失败！";

	public static final String CONNECT_SUCCESSED= "连接成功！";
	public static final String CONNECT_FAIL = "连接失败！";

	public static final String UPDATE_SUCCESSED = "修改成功！";
	public static final String UPDATE_FAIL = "修改失败！";

	public static final String DELETE_SUCCESSED = "删除成功！";
	public static final String DELETE_FAIL = "删除失败！";

	public static final String OPERATE_SUCCESSED = "操作成功！";
	public static final String OPERATE_FAIL = "操作失败！";

	public static final String SUBMIT_SUCCESSED = "提交成功！";
	public static final String SUBMIT_FAIL = "提交失败！";

	public static final String UPLOAD_SUCCESSED = "上传成功！";
	public static final String UPLOAD_FAIL = "上传失败！";

	public static final String SORT_SUCCESSED = "排序成功！";
	public static final String SORT_FAIL = "排序失败！";

	public static final String RECEIVE_SUCCESSED = "领取成功！";
	public static final String RECEIVE_FAIL = "领取失败！";

	public static final String QUARTZ_SUCCESSED = "启动定时任务成功！";
	public static final String QUARTZ_REMOVE_SUCCESSED = "关闭定时任务成功！";

	public static final String SAVE_FILE_TO_DIR_FAIL = "保存文件至磁盘失败！";
	public static final String DELETE_FILE_FROM_DIR_FAIL = "删除磁盘文件失败！";

	public static final String UNIQUE = "数据唯一";
	public static final String NOT_UNIQUE= "数据不唯一";
	public static final String NOT_UNIQUE_MESSAGE= "编码或名称重复！";
	public static final String NOT_UNIQUE_NAME= "名称重复！";
	public static final String NOT_ROLE= "抱歉，当前角色不支持查询！";
	public static final String UNABLE_TO_DELETE = "子节点不为空，无法删除！";

	public static final String XIA_WORK_SUCESS = "下发失败！";
	public static final String XIA_WORK_FAILD = "下发失败！";

	//******************  数据库记录状态 ！   ***************************

	public static final String Y = "Y";// 使用标记
	public static final String N = "N";// 删除标记

	// 受影响的行数
	public static final int ZERO = 0;
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;
	public static final int ELEVEN= 11;
	public static final int TWELVE = 12;


	public static final String STRING_ZERO = "0";
	public static final String STRING_ONE = "1";

	//默认分页大小
	public static final int DEFAULT_PAGE_SIZE = 10;
	//分页请求路径
	public static final String PAGE_SERVLET_PATH = "pageServletPath";
	public static final String PAGEINFO = "pageInfo";


	//******************  encoding  字符编码 ！  **********************

	public static final String ISO_8859_1 = "ISO-8859-1";
	public static final String GBK = "GBK";
	public static final String UTF_8 = "UTF-8";



	//******************  用于 String 方法 ！  **********************

	/**
	 * 空字符串
	 */
	public static final String EMPTY = "";
	/**
	 * 百分号，用于 模糊查询
	 */
	public static final String PERCENT = "%";
	/**
	 * 逗号，用于 split(",")
	 */
	public static final String COMMA = ",";
	/**
	 * 点，用于 lastIndexOf(".")
	 */
	public static final String DOT = ".";
	/**
	 * 冒号，用于excel导出的split(":")
	 */
	public static final String COLON = ":";


	//**********************   excel 导入、导出！   ***************************

	//用户代理
	public static final String USER_AGENT = "USER-AGENT";
	//用户代理
	public static final String DOT_XLS = ".xls";
	//指示 MIME 用户代理如何显示附加的文件。
	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	//response 的contentType
	public static final String OCTET_STREAM = "application/octet-stream;charset=UTF-8";

	//**********************   conf/config.properties   ***************************
	public static final String CONFIG_PROPERTIES = "conf/config.properties";


	//******************  超级管理员 ！   ********************

	//超级管理员  登录账号
	public static final String ROOT_USER_ACC = "root";
	public static final String ROOT_USER_NAME = "超级管理员";
	//超级管理员  主键
	public static final String ROOT_USER_ID = "00000000000000000000000000000000";
	//超级管理员  密码
	public static final String ROOT_PASSWORD = "Rxmes_2018";

	//*********************  排序字段   !  *******************************

	//CREATE_TIME
	public static final String CREATE_TIME = "CREATE_TIME";
	public static final String CREATE_TIME_DESC = "CREATE_TIME DESC";
	public static final String MODIFY_TIME = "MODIFY_TIME";
	public static final String MODIFY_TIME_DESC = "MODIFY_TIME DESC";
	//SORT_KEY
	public static final String SORT_KEY = "SORT_KEY";
	public static final String SORT_KEY_DESC = "SORT_KEY DESC";
	//SORT_KEY, CREATE_TIME
	public static final String SORT_KEY_CREATE_TIME = "SORT_KEY, CREATE_TIME";
	public static final String SORT_KEY_CREATE_TIME_DESC = "SORT_KEY, CREATE_TIME DESC"; //字典、设备自定义属性、场景模块关联表 使用！
	//SORT_CODE
	public static final String SORT_CODE = "SORT_CODE";
	public static final String SORT_CODE_DESC = "SORT_CODE DESC";
	//缺省日期格式
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS";
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_2 = "yyyy-MM-dd";
	public static final String DATEFORMAT2 = "yyyyMMdd";
	public static final String DATE_FORMAT_3 = "yyyy-MM";
	public static final String DATE_FORMAT_4 = "yyyy";

	public static final String DATE_FORMAT_6 = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_7 = "yyyyMMddHHmmssSSS";
	public static final String DATE_FORMAT_8 = "HHmm";

	public static final String DATE_FORMAT_9 = "yyyy年MM月dd日";


	//上传文件保存路径
	public static final String UPDATEURL = "upload/";
	//上传文件保存路径
	public static final String UPDATEURL_DELIVER = "deliver";
	//根节点图标固定路径
	public static final String ROOTICONURL = "upload/xiangmu.png";

	//认证Authorization
	public static final String AUTHORIZATION = "Authorization";
	//持票人token "Bearer "
	public static final String BEARER = "Bearer ";

}