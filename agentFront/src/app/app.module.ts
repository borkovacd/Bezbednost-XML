import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import { AppComponent } from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import {WelcomepageComponent} from './welcomepage/welcomepage.component';
import {AccomodationComponent} from './accomodation/accomodation.component';
import {AddEditAccomodationComponent} from "./add-edit-accomodation/add-edit-accomodation.component";

@NgModule({
  declarations: [
    AppComponent,
    WelcomepageComponent,
    AccomodationComponent,
    AddEditAccomodationComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,

    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
