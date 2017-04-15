import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ManufacturerComponent } from './manufacturer.component';
import { ManufacturerDetailComponent } from './manufacturer-detail.component';
import { ManufacturerPopupComponent, ManufacturerDialogComponent } from './manufacturer-dialog.component';
import { ManufacturerDeletePopupComponent } from './manufacturer-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ManufacturerResolvePagingParams implements Resolve<any> {

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

export const manufacturerRoute: Routes = [
  {
    path: 'manufacturer',
    component: ManufacturerComponent,
    resolve: {
      'pagingParams': ManufacturerResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.manufacturer.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'manufacturer-new',
    component: ManufacturerDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.manufacturer.home.title'
    },
  }, {
    path: 'manufacturer/:id',
    component: ManufacturerDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.manufacturer.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const manufacturerPopupRoute: Routes = [
  {
    path: 'manufacturer/:id/edit',
    component: ManufacturerPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.manufacturer.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'manufacturer/:id/delete',
    component: ManufacturerDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.manufacturer.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
