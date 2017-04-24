import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EmailSettingComponent } from './email-setting.component';
import { EmailSettingDetailComponent } from './email-setting-detail.component';
import { EmailSettingPopupComponent, EmailSettingDialogComponent } from './email-setting-dialog.component';
import { EmailSettingDeletePopupComponent } from './email-setting-delete-dialog.component';

import { Principal } from '../../shared';

export const emailSettingRoute: Routes = [
  {
    path: 'email-setting',
    component: EmailSettingComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.emailSetting.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'email-setting/:id',
    component: EmailSettingDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.emailSetting.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'email-setting-new',
    component: EmailSettingDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.emailSetting.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'email-setting/:id/edit',
    component: EmailSettingDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.emailSetting.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const emailSettingPopupRoute: Routes = [
  
  
  {
    path: 'email-setting/:id/delete',
    component: EmailSettingDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.emailSetting.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
