package com.bm.tzj.kc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bm.api.BaseApi;
import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.adapter.CourseCommentAdapter;
import com.bm.dialog.ToastDialog;
import com.bm.dialog.UtilDialog;
import com.bm.entity.Model;
import com.bm.entity.SigninInfo;
import com.richer.tzjjl.R;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonListResult;
import com.lib.http.result.CommonResult;
import com.lib.tool.Pager;
import com.lib.widget.RefreshViewPD;
import com.lib.widget.refush.SwipyRefreshLayout;
import com.lib.widget.refush.SwipyRefreshLayout.OnRefreshListener;
import com.lib.widget.refush.SwipyRefreshLayoutDirection;

/**
 * 我的课程
 *
 * @author wanghy
 */
public class CourseCommentFrameLayout extends FrameLayout implements CourseCommentAdapter.OnSeckillClick, OnRefreshListener {
    private Context context;
    private RefreshViewPD refresh_view;
    /**
     * 分页器
     */
    public Pager pager = new Pager(10);
    private ListView lv_course_comment;
    private ImageView img_noData;
    private String state;
    private CourseCommentAdapter adapter;
    private List<SigninInfo> list = new ArrayList<SigninInfo>();
    private List<Model> listRefused = new ArrayList<Model>();
    private SwipyRefreshLayout mSwipyRefreshLayout;
    public String strSignAllCount = "0", strSignCount = "0", strUnsignCount = "0";//参与（宝贝人数）,已签到数,未签到数
    Intent intent = null;
    private int pos = -1;
    String degree, strGoodsId = "", strComment = "", strTag = "1", strMedalIds = "", babyId, goodsName;


    public static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    public static final String bucketTest = "han-shan-test";
    public static final String STSSERVER = "http://softlst.com:8888/tongZiJun/api/OSSApi/getSTS";//STS 地址
    public static final String callbackAddress = "OSSApi/callback?fkid=%s&filename=%s";//


    public OSS oss ;


    public CourseCommentFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CourseCommentFrameLayout(Context context, String state, String degree, String goodsId, String babayId, String goodsName) {
        super(context);
        this.context = context;
        this.state = state;
        this.degree = degree;
        this.strGoodsId = goodsId;
        this.babyId = babyId;
        this.goodsName = goodsName;
        if (state.length() == 0) {
            this.state = "";
        }

        init();
    }

    public void init() {
        // instance=this;

        LayoutInflater.from(context).inflate(R.layout.course_comment_framelayout,
                this);
        img_noData = (ImageView) findViewById(R.id.img_noData);
        img_noData.setVisibility(View.VISIBLE);
        mSwipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        mSwipyRefreshLayout.setColorScheme(R.color.color1, R.color.color2, R.color.color3, R.color.color4);

        lv_course_comment = (ListView) findViewById(R.id.lv_course_comment);

        adapter = new CourseCommentAdapter(context, list);
        lv_course_comment.setAdapter(adapter);
        adapter.setOnSeckillClick(this);
        oss = initOss();
    }

    public OSS initOss() {

        //  OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(ACCESS_ID, ACCESS_KEY);
        String STSSERVER = "http://softlst.com:8888/tongZiJun/api/OSSApi/getSTS";//STS 地址
        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(STSSERVER);
        //该配置类如果不设置，会有默认配置，具体可看该类
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        return new OSSClient(context, endpoint, credentialProvider);
    }

    ArrayList filePaths;
    public void upDataImage(String pkid) {
        filePaths = new ArrayList (((CourseCommentAc) context).uploadListImg);
        imageLength = filePaths.size();
        Log.d("updataCallbackAddress","准备上传第"+imageIndex+"张");
        CourseCommentAc.intance.showProgressDialog();
        try {
            if(imageLength>0)
            updataimg(pkid);
        }catch (Exception e){
            e.printStackTrace();
            App.toast("上传失败");
            CourseCommentAc.intance.hideProgressDialog();
            handler.sendEmptyMessage(400);

        }
    }
    int imageIndex =0;
    int imageLength = 0 ;
    private void updataimg(final String pkid){
        String  filePath = (String) filePaths.get(imageIndex);
        String  suffix  =  filePath.substring(filePath.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String imageName = uuid +suffix;
        PutObjectRequest put = new PutObjectRequest(bucketTest, "img/user/growth/"+imageName, filePath);
        final String imgPath = String.format(callbackAddress,  pkid,imageName);
//        put.setCallbackParam(new HashMap<String, String>() {
//            {
//                Log.d("callbackUrl", "callbackUrl start" + imgPath);
//                put("callbackUrl", imgPath);
//                //callbackBody可以自定义传入的信息
//                put("callbackBody", "{}");
//                Log.d("callbackUrl", "callbackUrl end");
//            }
//        });
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                Log.d("updataCallbackAddress","上传成功");
                updataCallbackAddress(pkid,imgPath);
            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                Log.d("updataCallbackAddress","上传失败");
                App.toast("上传失败");
                CourseCommentAc.intance.hideProgressDialog();
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }

                handler.sendEmptyMessage(400);
            }
        });
    }
    //上传图片成功回调服务器接口
    public  void updataCallbackAddress(final String fkid, String url){
        Log.d("updataCallbackAddress","开始回调"+url);
        UserManager.getInstance().httpGet(context, url, new ServiceCallback<CommonResult<Object>>() {
            @Override
            public void done(int what, CommonResult<Object> obj) {
                Log.e("updataCallbackAddress", "回调返回值："+obj.data.toString());
                Log.e("updataCallbackAddress", "第"+imageIndex+"张上传完毕");
               if(obj.status==1){
                   imageIndex++;
                   Log.d("updataCallbackAddress","回调成功"+url);
                   if (imageIndex<imageLength){
                       Log.d("updataCallbackAddress","准备上传第"+imageIndex+"张");
                       updataimg(fkid);
                   }else {
                       //上传完毕
                        imageIndex =0;
                        imageLength = 0 ;
                       Log.e("updataCallbackAddress", "全部张上传完毕");
                       Looper.prepare();
                       App.toast("上传成功");
                       CourseCommentAc.intance.hideProgressDialog();
                       //Toast.makeText(context,"上传成功",Toast.LENGTH_SHORT).show();
                       Looper.loop();


                   }
               }else {
                   error(obj.status+":"+obj.msg);
               }
            }

            @Override
            public void error(String msg) {
                Log.e("updataCallbackAddress", "回调失败:"+msg);
                CourseCommentAc.intance.hideProgressDialog();
                Looper.prepare();
                App.toast("上传失败");
                Looper.loop();
                handler.sendEmptyMessage(400);
            }
        });
    }
    public void reFresh() {
        pager.setFirstPage();
        list.clear();
        getCommentInfo();
        //getMedalListInfo();
    }

    /**
     * 获取评论信息
     */
    public void getCommentInfo() {
        if (null == App.getInstance().getCoach()) {
            return;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("status", state);//查询状态   0 未评价  1 已评价  2 所有
        map.put("pageCount", "10");//每页展示条数
        map.put("pageNum", pager.nextPageToStr());
        map.put("userid", App.getInstance().getCoach().coachId);//教练ID
        map.put("baseId", strGoodsId);
        CourseCommentAc.intance.showProgressDialog();
        UserManager.getInstance().getTzjgoodsPassL(context, map, new ServiceCallback<CommonListResult<SigninInfo>>() {

            @Override
            public void error(String msg) {
                CourseCommentAc.intance.hideProgressDialog();
                CourseCommentAc.intance.dialogToast(msg);
            }

            @Override
            public void done(int what, CommonListResult<SigninInfo> obj) {
                CourseCommentAc.intance.hideProgressDialog();
                if (obj.data.size() > 0) {
                    pager.setCurrentPage(pager.nextPage(), list.size());
                    ArrayList<SigninInfo> signininfos = obj.data;
                    for (int i = 0; i < signininfos.size(); i++) {
                        signininfos.get(i).goodsName = goodsName;
                    }
                    list.addAll(signininfos);
                }
                setData();
            }
        });
    }

    private void setData() {
        if (list.size() == 0) {
            img_noData.setVisibility(View.VISIBLE);
        } else {
            img_noData.setVisibility(View.GONE);
            lv_course_comment.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取勋章信息
     */
    public void getMedalListInfo() {
        listRefused.clear();
        HashMap<String, String> map = new HashMap<String, String>();
        //此接口有问题 一直报错 连接不上
        map.put("baseId", strGoodsId);
        UserManager.getInstance().getTzjcoachGoodsMedallist(context, map, new ServiceCallback<CommonListResult<Model>>() {

            @Override
            public void error(String msg) {
                CourseCommentAc.intance.dialogToast(msg);
            }

            @Override
            public void done(int what, CommonListResult<Model> obj) {
                if (obj.data.size() > 0) {
                    listRefused.addAll(obj.data);
                    Model info = new Model();
                    info.medalId = "-1";
                    info.medalName = "全选";
                    listRefused.add(info);
                }
            }
        });

    }

    @Override
    public void onSeckillClick(int position, int type) {
        pos = position;
        String strName = list.get(position).realName == null ? "未知人" : list.get(position).realName;
        if (type == 1) {// 评价
            if (!"4".equals(degree)) {   //城市营地类型和户外俱乐部类型：弹出评分框1      期大露营类型：弹出评分框2
                ((CourseCommentAc) context).dialogTwoBtnCommResult("对" + strName + "的评价", "取消", "提交", handler, 1);
            } else {
                ((CourseCommentAc) context).dialogTwoBtnCommTwoResult(listRefused, "对" + strName + "的评价", "取消", "提交", handler, 2);
            }
        }
    }


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:      //评分弹出框1  城市营地 户外俱乐部
                    Model mInfo = (Model) msg.obj;
                    strComment = mInfo.content;
                    strTag = mInfo.degree;
                    submitComment();
                    //upDataImage(148+"");
                    break;
                case 2:  //评分弹出框2   暑期大陆营
                    strMedalIds = "";
                    List<Model> lists = (List<Model>) msg.obj;
                    for (int i = 0; i < lists.size(); i++) {
                        if (!lists.get(i).medalId.equals("-1")) {
                            strMedalIds += lists.get(i).medalId + ",";
                        }
                    }
                    strComment = lists.get(0).content.toString().trim();
                    submitComment();
                    UtilDialog.listRefu.clear();  //清除之前选中的数据
                    break;
                case 400:
                    UtilDialog.dialogTwoBtnResultCode(context,"上传失败，是否继续重新上传","取消","继续上传",handler,401);
                    break;
                case 401:
                    //UtilDialog.dialogTwoBtnResultCode(context,"上传失败，是否继续重新上传","取消","继续上传",handler,401);
                    upDataImage(fkid);
                    break;
            }
            ;
        }

        ;
    };





    String fkid;
    /**
     * 添加评论
     */
    public void submitComment() {
        if (null == App.getInstance().getCoach()) {
            return;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("coachId", App.getInstance().getCoach().coachId);//教练ID
        map.put("content", strComment);
        map.put("baseId", strGoodsId);//课程ID
        map.put("isPass", strTag);//是否通过
        map.put("userid", list.get(pos).userId);//评价人的ID
        map.put("goodsType", degree);
        map.put("medalIds", strMedalIds);
        map.put("babyId", list.get(pos).babyId);

        CourseCommentAc.intance.showProgressDialog();
        UserManager.getInstance().getTzjcoachAddPass(context, new ArrayList<String>(), map, new ServiceCallback<CommonResult<String>>() {

            @Override
            public void error(String msg) {
                CourseCommentAc.intance.dialogToast(msg);
                CourseCommentAc.intance.hideProgressDialog();
                App.toast("评论失败");
            }

            @Override
            public void done(int what, CommonResult<String> obj) {
                //CourseCommentAc.intance.hideProgressDialog();
                CourseCommentAc.intance.getDate();
                CourseCommentAc.intance.getPassCount();
                fkid = obj.data;//148
                App.toast("评论成功，正在上传图片");
                upDataImage(fkid);
            }
        });


    }


    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Hide the refresh after 2sec
                    ((CourseCommentAc) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pager.setFirstPage();
                            list.clear();
                            getCommentInfo();
                            // 刷新设置
                            mSwipyRefreshLayout.setRefreshing(false);

                        }
                    });
                }
            }, 2000);

        } else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Hide the refresh after 2sec
                    ((CourseCommentAc) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getCommentInfo();

                            // 刷新设置
                            mSwipyRefreshLayout.setRefreshing(false);


                        }
                    });
                }
            }, 2000);
        }
    }

}
