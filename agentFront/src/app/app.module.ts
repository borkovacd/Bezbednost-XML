import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import { AppComponent } from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import {WelcomepageComponent} from './welcomepage/welcomepage.component';
import {AccomodationComponent} from './accomodation/accomodation.component';
import {AddEditAccomodationComponent} from './add-edit-accomodation/add-edit-accomodation.component';
import {AccomodationService} from './service/accomodation.service';
import {AdditionalServicesService} from './service/additionalServices.service';
import {CityService} from './service/city.service';
import {CategoryService} from './service/category.service';
import {TypeAccomodationService} from './service/typeAccomodation.service';

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
  providers: [
    AccomodationService,
    AdditionalServicesService,
    CityService,
    CategoryService,
    TypeAccomodationService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
