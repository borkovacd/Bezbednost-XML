import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FormsModule} from '@angular/forms';
import {HomeComponent} from './home/home.component';
import {CreateCertificateComponent} from './createCertificate/createCertificate.component';
import {CeritifcatesComponent} from './certificates/ceritifcates.component';

const routes: Routes = [
  {path: '', component: HomeComponent, pathMatch: 'full'},
  {path: 'createCertificate', component: CreateCertificateComponent},
  {path: 'certificates', component: CeritifcatesComponent},
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true}), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }

