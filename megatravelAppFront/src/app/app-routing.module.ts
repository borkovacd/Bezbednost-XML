import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FormsModule} from '@angular/forms';
import {HomeComponent} from './home/home.component';
import {CreateCertificateComponent} from './createCertificate/createCertificate.component';
import {CeritifcatesComponent} from './certificates/ceritifcates.component';
import {WelcomepageComponent} from './welcomepage/welcomepage.component';
import {LoginComponent} from './login/login.component';

const routes: Routes = [
  {path: '', component: WelcomepageComponent, pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent},
  {path: 'createCertificate', component: CreateCertificateComponent},
  {path: 'certificates', component: CeritifcatesComponent},
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true}), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }

