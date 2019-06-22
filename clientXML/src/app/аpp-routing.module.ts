import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FormsModule} from '@angular/forms';
import {WelcomepageComponent} from './welcomepage/welcomepage.component';
import {RegistrationComponent} from './registration/registration.component';

const routes: Routes = [
  {path: '', component: WelcomepageComponent, pathMatch: 'full'},
  {path: 'registration', component: RegistrationComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true}), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
