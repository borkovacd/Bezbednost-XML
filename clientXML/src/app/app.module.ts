import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import {WelcomepageComponent} from './welcomepage/welcomepage.component';
import {AppRoutingModule} from './аpp-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import {UiModule} from './ui/ui.module';
import {RegistrationComponent} from './registration/registration.component';
import {UserService} from './service/user.service';


@NgModule({
  declarations: [
    AppComponent,
    WelcomepageComponent,
    LoginComponent,
    RegistrationComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    UiModule,

    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
   // UiModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
