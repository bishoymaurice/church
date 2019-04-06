import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { MemberRegisterComponent } from './member-register/member-register.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { MinisterService } from 'src/swagger/api/minister.service';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    MemberRegisterComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [MinisterService, HttpClient],
  bootstrap: [AppComponent]
})
export class AppModule { }
