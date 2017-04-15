import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MerchantConfigurationComponent } from './merchant-configuration.component';
import { MerchantConfigurationDetailComponent } from './merchant-configuration-detail.component';
import { MerchantConfigurationPopupComponent, MerchantConfigurationDialogComponent } from './merchant-configuration-dialog.component';
import { MerchantConfigurationDeletePopupComponent } from './merchant-configuration-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class MerchantConfigurationResolvePagingParams implements Resolve<any> {

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

export const merchantConfigurationRoute: Routes = [
  {
    path: 'merchant-configuration',
    component: MerchantConfigurationComponent,
    resolve: {
      'pagingParams': MerchantConfigurationResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.merchantConfiguration.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'merchant-configuration-new',
    component: MerchantConfigurationDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.merchantConfiguration.home.title'
    },
  }, {
    path: 'merchant-configuration/:id',
    component: MerchantConfigurationDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.merchantConfiguration.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const merchantConfigurationPopupRoute: Routes = [
  {
    path: 'merchant-configuration/:id/edit',
    component: MerchantConfigurationPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.merchantConfiguration.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'merchant-configuration/:id/delete',
    component: MerchantConfigurationDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.merchantConfiguration.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
