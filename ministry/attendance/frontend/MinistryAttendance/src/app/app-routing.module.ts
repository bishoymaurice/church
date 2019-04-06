import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { MemberRegisterComponent } from 'src/app/member-register/member-register.component';
import { PageNotFoundComponent } from 'src/app/page-not-found/page-not-found.component';

const routes: Routes = [
  { path: 'ministry/attendance/:id', component: MemberRegisterComponent },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule, RouterModule.forRoot(
      routes,
      {
        //enableTracing: true  // <-- enable this for debugging purposes only
      }
    )
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
