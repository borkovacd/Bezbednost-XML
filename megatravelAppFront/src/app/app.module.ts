import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';


import { AppComponent } from './app.component';
import {HomeComponent} from './home/home.component';
import {AppRoutingModule} from './app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import {CreateCertificateComponent} from './createCertificate/createCertificate.component';
import {SecurityService} from './service/security.service';
import {CeritifcatesComponent} from './certificates/ceritifcates.component';
import {LoginComponent} from './login/login.component';
import {WelcomepageComponent} from './welcomepage/welcomepage.component';
import {UserService} from "./service/user.service";


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CreateCertificateComponent,
    CeritifcatesComponent,
    LoginComponent,
    WelcomepageComponent,
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
    UserService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
