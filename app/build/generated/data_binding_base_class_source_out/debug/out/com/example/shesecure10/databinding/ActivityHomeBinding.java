// Generated by view binder compiler. Do not edit!
package com.example.shesecure10.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.shesecure10.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityHomeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView alert;

  @NonNull
  public final TextView companion;

  @NonNull
  public final CardView highdanger;

  @NonNull
  public final ImageView homebg;

  @NonNull
  public final ImageView imageView13;

  @NonNull
  public final ImageView imageView15;

  @NonNull
  public final ImageView imageView16;

  @NonNull
  public final ImageView imageView2;

  @NonNull
  public final ImageView imageView3;

  @NonNull
  public final ImageView imageView4;

  @NonNull
  public final ImageView imageView5;

  @NonNull
  public final ImageView imageView6;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final LinearLayout linearLayout2;

  @NonNull
  public final LinearLayout linearLayout3;

  @NonNull
  public final LinearLayout linearLayout4;

  @NonNull
  public final TextView route;

  @NonNull
  public final CardView routeplanner;

  @NonNull
  public final CardView selfdefense;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView tutorial;

  @NonNull
  public final CardView virtualcompanion;

  private ActivityHomeBinding(@NonNull ConstraintLayout rootView, @NonNull TextView alert,
      @NonNull TextView companion, @NonNull CardView highdanger, @NonNull ImageView homebg,
      @NonNull ImageView imageView13, @NonNull ImageView imageView15,
      @NonNull ImageView imageView16, @NonNull ImageView imageView2, @NonNull ImageView imageView3,
      @NonNull ImageView imageView4, @NonNull ImageView imageView5, @NonNull ImageView imageView6,
      @NonNull LinearLayout linearLayout, @NonNull LinearLayout linearLayout2,
      @NonNull LinearLayout linearLayout3, @NonNull LinearLayout linearLayout4,
      @NonNull TextView route, @NonNull CardView routeplanner, @NonNull CardView selfdefense,
      @NonNull TextView textView3, @NonNull TextView tutorial, @NonNull CardView virtualcompanion) {
    this.rootView = rootView;
    this.alert = alert;
    this.companion = companion;
    this.highdanger = highdanger;
    this.homebg = homebg;
    this.imageView13 = imageView13;
    this.imageView15 = imageView15;
    this.imageView16 = imageView16;
    this.imageView2 = imageView2;
    this.imageView3 = imageView3;
    this.imageView4 = imageView4;
    this.imageView5 = imageView5;
    this.imageView6 = imageView6;
    this.linearLayout = linearLayout;
    this.linearLayout2 = linearLayout2;
    this.linearLayout3 = linearLayout3;
    this.linearLayout4 = linearLayout4;
    this.route = route;
    this.routeplanner = routeplanner;
    this.selfdefense = selfdefense;
    this.textView3 = textView3;
    this.tutorial = tutorial;
    this.virtualcompanion = virtualcompanion;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.alert;
      TextView alert = ViewBindings.findChildViewById(rootView, id);
      if (alert == null) {
        break missingId;
      }

      id = R.id.companion;
      TextView companion = ViewBindings.findChildViewById(rootView, id);
      if (companion == null) {
        break missingId;
      }

      id = R.id.highdanger;
      CardView highdanger = ViewBindings.findChildViewById(rootView, id);
      if (highdanger == null) {
        break missingId;
      }

      id = R.id.homebg;
      ImageView homebg = ViewBindings.findChildViewById(rootView, id);
      if (homebg == null) {
        break missingId;
      }

      id = R.id.imageView13;
      ImageView imageView13 = ViewBindings.findChildViewById(rootView, id);
      if (imageView13 == null) {
        break missingId;
      }

      id = R.id.imageView15;
      ImageView imageView15 = ViewBindings.findChildViewById(rootView, id);
      if (imageView15 == null) {
        break missingId;
      }

      id = R.id.imageView16;
      ImageView imageView16 = ViewBindings.findChildViewById(rootView, id);
      if (imageView16 == null) {
        break missingId;
      }

      id = R.id.imageView2;
      ImageView imageView2 = ViewBindings.findChildViewById(rootView, id);
      if (imageView2 == null) {
        break missingId;
      }

      id = R.id.imageView3;
      ImageView imageView3 = ViewBindings.findChildViewById(rootView, id);
      if (imageView3 == null) {
        break missingId;
      }

      id = R.id.imageView4;
      ImageView imageView4 = ViewBindings.findChildViewById(rootView, id);
      if (imageView4 == null) {
        break missingId;
      }

      id = R.id.imageView5;
      ImageView imageView5 = ViewBindings.findChildViewById(rootView, id);
      if (imageView5 == null) {
        break missingId;
      }

      id = R.id.imageView6;
      ImageView imageView6 = ViewBindings.findChildViewById(rootView, id);
      if (imageView6 == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.linearLayout2;
      LinearLayout linearLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout2 == null) {
        break missingId;
      }

      id = R.id.linearLayout3;
      LinearLayout linearLayout3 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout3 == null) {
        break missingId;
      }

      id = R.id.linearLayout4;
      LinearLayout linearLayout4 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout4 == null) {
        break missingId;
      }

      id = R.id.route;
      TextView route = ViewBindings.findChildViewById(rootView, id);
      if (route == null) {
        break missingId;
      }

      id = R.id.routeplanner;
      CardView routeplanner = ViewBindings.findChildViewById(rootView, id);
      if (routeplanner == null) {
        break missingId;
      }

      id = R.id.selfdefense;
      CardView selfdefense = ViewBindings.findChildViewById(rootView, id);
      if (selfdefense == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.tutorial;
      TextView tutorial = ViewBindings.findChildViewById(rootView, id);
      if (tutorial == null) {
        break missingId;
      }

      id = R.id.virtualcompanion;
      CardView virtualcompanion = ViewBindings.findChildViewById(rootView, id);
      if (virtualcompanion == null) {
        break missingId;
      }

      return new ActivityHomeBinding((ConstraintLayout) rootView, alert, companion, highdanger,
          homebg, imageView13, imageView15, imageView16, imageView2, imageView3, imageView4,
          imageView5, imageView6, linearLayout, linearLayout2, linearLayout3, linearLayout4, route,
          routeplanner, selfdefense, textView3, tutorial, virtualcompanion);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
