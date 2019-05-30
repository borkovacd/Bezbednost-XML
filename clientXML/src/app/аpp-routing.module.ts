import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FormsModule} from '@angular/forms';
import {WelcomepageComponent} from './welcomepage/welcomepage.component';

const routes: Routes = [
  {path: '', component: WelcomepageComponent, pathMatch: 'full'},

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true}), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
