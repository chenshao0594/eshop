import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ManufacturerDescriptionComponent } from './manufacturer-description.component';
import { ManufacturerDescriptionDetailComponent } from './manufacturer-description-detail.component';
import { ManufacturerDescriptionPopupComponent, ManufacturerDescriptionDialogComponent } from './manufacturer-description-dialog.component';
import { ManufacturerDescriptionDeletePopupComponent } from './manufacturer-description-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ManufacturerDescriptionResolvePagingParams implements Resolve<any> {

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

export const manufacturerDescriptionRoute: Routes = [
  {
    path: 'manufacturer-description',
    component: ManufacturerDescriptionComponent,
    resolve: {
      'pagingParams': ManufacturerDescriptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.manufacturerDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'manufacturer-description-new',
    component: ManufacturerDescriptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.manufacturerDescription.home.title'
    },
  }, {
    path: 'manufacturer-description/:id',
    component: ManufacturerDescriptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.manufacturerDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const manufacturerDescriptionPopupRoute: Routes = [
  {
    path: 'manufacturer-description/:id/edit',
    component: ManufacturerDescriptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.manufacturerDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'manufacturer-description/:id/delete',
    component: ManufacturerDescriptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.manufacturerDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
