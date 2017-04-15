import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SystemNotificationComponent } from './system-notification.component';
import { SystemNotificationDetailComponent } from './system-notification-detail.component';
import { SystemNotificationPopupComponent, SystemNotificationDialogComponent } from './system-notification-dialog.component';
import { SystemNotificationDeletePopupComponent } from './system-notification-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class SystemNotificationResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const systemNotificationRoute: Routes = [
  {
    path: 'system-notification',
    component: SystemNotificationComponent,
    resolve: {
      'pagingParams': SystemNotificationResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.systemNotification.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'system-notification-new',
    component: SystemNotificationDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.systemNotification.home.title'
    },
  }, {
    path: 'system-notification/:id',
    component: SystemNotificationDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.systemNotification.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const systemNotificationPopupRoute: Routes = [
  {
    path: 'system-notification/:id/edit',
    component: SystemNotificationPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.systemNotification.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'system-notification/:id/delete',
    component: SystemNotificationDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.systemNotification.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
