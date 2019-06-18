import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FormsModule} from '@angular/forms';

import {WelcomepageComponent} from './welcomepage/welcomepage.component';
import {AddEditAccomodationComponent} from './add-edit-accomodation/add-edit-accomodation.component';
import {LogInComponent} from './log-in/log-in.component';
import {RoomComponent} from './room/room.component';
import {AddEditRoomComponent} from './add-edit-room/add-edit-room.component';
import {PricelistComponent} from './pricelist/pricelist.component';
import {AddPricelistComponent} from './add-pricelist/add-pricelist.component';


const routes: Routes = [
  {path: '', component: LogInComponent, pathMatch: 'full'},
  {path: 'log-in', component: LogInComponent},
  {path: 'welcomepage', component: WelcomepageComponent},
  {path: 'welcomepage/room/:idA', component: RoomComponent},
  {path: 'welcomepage/room/:idA/pricelist/:idR', component: PricelistComponent},
  {path: 'welcomepage/room/:idA/pricelist/:mode/:idR', component: AddPricelistComponent},
  {path: 'welcomepage/room/:idA/:mode/:idR', component: AddEditRoomComponent},
  {path: ':mode/accomodation/:idA', component: AddEditAccomodationComponent},



];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true}), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
