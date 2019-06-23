import {RouterModule, Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {NgModule} from '@angular/core';
import {WelcomePageComponent} from './welcome-page/welcome-page.component';
import {LoginComponent} from './login/login.component';
import {AddAgentComponent} from './add-agent/add-agent.component';
import {AddCategoryComponent} from './add-category/add-category.component';
import {AddAdditionalServiceComponent} from './add-additionalService/add-additionalService.component';
import {AddAccommodationTypeComponent} from './add-accommodationType/add-accommodationType.component';
import {HomeComponent} from './home/home.component';
import {ProfileComponent} from './profile/profile.component';
import {ListAgentComponent} from './list-agent/list-agent.component';
import {ListCategoryComponent} from './list-category/list-category.component';
import {ListUserComponent} from './list-user/list-user.component';

const routes: Routes = [
  {path: '', component: WelcomePageComponent, pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'addAgent', component: AddAgentComponent},
  {path: 'home', component: HomeComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'listAgents', component: ListAgentComponent},
  {path: 'addAgent', component: AddAgentComponent},
  {path: 'addCategory', component : AddCategoryComponent},
  {path: 'addAdditionalService', component : AddAdditionalServiceComponent},
  {path: 'addAccommodationTypes', component : AddAccommodationTypeComponent},
  {path: 'listCategories', component: ListCategoryComponent},
  {path: 'listUsers', component: ListUserComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true}), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
