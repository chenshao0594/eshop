import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SystemConfigurationComponent } from './system-configuration.component';
import { SystemConfigurationDetailComponent } from './system-configuration-detail.component';
import { SystemConfigurationPopupComponent, SystemConfigurationDialogComponent } from './system-configuration-dialog.component';
import { SystemConfigurationDeletePopupComponent } from './system-configuration-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class SystemConfigurationResolvePagingParams implements Resolve<any> {

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

export const systemConfigurationRoute: Routes = [
  {
    path: 'system-configuration',
    component: SystemConfigurationComponent,
    resolve: {
      'pagingParams': SystemConfigurationResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.systemConfiguration.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'system-configuration-new',
    component: SystemConfigurationDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.systemConfiguration.home.title'
    },
  }, {
    path: 'system-configuration/:id',
    component: SystemConfigurationDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.systemConfiguration.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const systemConfigurationPopupRoute: Routes = [
  {
    path: 'system-configuration/:id/edit',
    component: SystemConfigurationPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.systemConfiguration.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'system-configuration/:id/delete',
    component: SystemConfigurationDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.systemConfiguration.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
