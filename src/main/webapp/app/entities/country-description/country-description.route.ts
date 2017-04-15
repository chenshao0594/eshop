import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CountryDescriptionComponent } from './country-description.component';
import { CountryDescriptionDetailComponent } from './country-description-detail.component';
import { CountryDescriptionPopupComponent, CountryDescriptionDialogComponent } from './country-description-dialog.component';
import { CountryDescriptionDeletePopupComponent } from './country-description-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CountryDescriptionResolvePagingParams implements Resolve<any> {

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

export const countryDescriptionRoute: Routes = [
  {
    path: 'country-description',
    component: CountryDescriptionComponent,
    resolve: {
      'pagingParams': CountryDescriptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.countryDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'country-description-new',
    component: CountryDescriptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.countryDescription.home.title'
    },
  }, {
    path: 'country-description/:id',
    component: CountryDescriptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.countryDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const countryDescriptionPopupRoute: Routes = [
  {
    path: 'country-description/:id/edit',
    component: CountryDescriptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.countryDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'country-description/:id/delete',
    component: CountryDescriptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.countryDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
