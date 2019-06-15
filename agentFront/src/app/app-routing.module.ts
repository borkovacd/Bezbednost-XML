import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FormsModule} from '@angular/forms';

import {WelcomepageComponent} from './welcomepage/welcomepage.component';
import {AddEditAccomodationComponent} from './add-edit-accomodation/add-edit-accomodation.component';
import {LogInComponent} from './log-in/log-in.component';


const routes: Routes = [
  {path: '', component: LogInComponent, pathMatch: 'full'},
  {path: 'log-in', component: LogInComponent},
  {path: 'welcomepage', component: WelcomepageComponent},
  {path: ':mode/accomodation', component: AddEditAccomodationComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true}), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
