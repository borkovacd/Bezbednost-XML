import {RouterModule, Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {NgModule} from '@angular/core';
import {WelcomePageComponent} from './welcome-page/welcome-page.component';
import {LoginComponent} from './login/login.component';
import {AddAgentComponent} from './add-agent/add-agent.component';
import {HomeComponent} from './home/home.component';
import {ProfileComponent} from './profile/profile.component';

const routes: Routes = [
  {path: '', component: WelcomePageComponent, pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'addAgent', component: AddAgentComponent},
  {path: 'home', component: HomeComponent},
  {path: 'profile', component: ProfileComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true}), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
