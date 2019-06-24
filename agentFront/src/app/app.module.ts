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
import {AgentService} from './service/agent.service';
import {LogInComponent} from './log-in/log-in.component';
import {CountryService} from './service/country.service';
import {RoomComponent} from './room/room.component';
import {RoomService} from './service/room.service';
import {AddEditRoomComponent} from './add-edit-room/add-edit-room.component';
import {PricelistComponent} from './pricelist/pricelist.component';
import {AddPricelistComponent} from './add-pricelist/add-pricelist.component';
import {PricelistService} from './service/pricelist.service';
import {ReservationComponent} from './reservation/reservation.component';
import {ReservationService} from './service/reservation.service';
import {AddReservationComponent} from './add-reservation/add-reservation.component';
import {ReservationAgentService} from './service/reservationAgent.service';
import {MessageComponent} from './message/message.component';
import {MessageService} from './service/message.service';

@NgModule({
  declarations: [
    AppComponent,
    WelcomepageComponent,
    AccomodationComponent,
    AddEditAccomodationComponent,
    LogInComponent,
    RoomComponent,
    AddEditRoomComponent,
    PricelistComponent,
    AddPricelistComponent,
    ReservationComponent,
    AddReservationComponent,
    MessageComponent,
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
    AgentService,
    CountryService,
    RoomService,
    PricelistService,
    ReservationService,
    ReservationAgentService,
    MessageService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
