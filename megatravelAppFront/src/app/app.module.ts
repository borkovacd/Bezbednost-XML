import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';


import { AppComponent } from './app.component';
import {HomeComponent} from './home/home.component';
import {AppRoutingModule} from './app-routing.module';
import {Router, RouterModule, Routes} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {CreateCertificateComponent} from './createCertificate/createCertificate.component';
import {SubjectSoftwareModel} from './model/subjectSoftware.model';
import {SecurityService} from './service/security.service';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CreateCertificateComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,

    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    SecurityService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
