package project.cn.edu.tongji.sse.nowfitness.view.publishMomentView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;
import project.cn.edu.tongji.sse.nowfitness.view.method.PermissionMethod;

public class PubishMomentView extends AppCompatActivity implements PublishMomentMethod {
    private Toolbar toolbar;
    private ImageView photoSelected;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.publish_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_view);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.publish_menu);
        photoSelected = (ImageView) findViewById(R.id.display_image);
        setListener();
    }

    private void setListener(){
        photoSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstantMethod.userMatisseFromActivity(PubishMomentView.this);
            }
        });
        photoSelected.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Glide.with(getWindow().getDecorView()).load(R.drawable.camera)
                        .transition(new DrawableTransitionOptions().crossFade(1000))
                        .into(photoSelected);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantMethod.REQUEST_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            List<Uri> imageUri=  new ArrayList<>();
            imageUri = Matisse.obtainResult(data);
            Log.d("1111", "onActivityResult:succsess Image ");
            Glide.with(getWindow().getDecorView()).load(imageUri.get(0)).into(photoSelected);
        }
    }
}
