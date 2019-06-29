import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FormsModule} from '@angular/forms';
import {WelcomepageComponent} from './welcomepage/welcomepage.component';
import {RegistrationComponent} from './registration/registration.component';
import {LoginComponent} from './login/login.component';
import {ListReservationComponent} from './list-reservation/list-reservation.component';
import {MessageComponent} from './messages/message.component';
import {CommentComponent} from './comment/comment.component';
import {RoomRatingComponent} from './roomRating/roomRating.component';

const routes: Routes = [
  {path: '', component: WelcomepageComponent, pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'myReservations', component: ListReservationComponent},
  {path: 'myReservations/:id/rating', component: CommentComponent},
  {path: 'myReservations/room/:idRoom', component: RoomRatingComponent},

  {path: 'messages', component: MessageComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true}), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
