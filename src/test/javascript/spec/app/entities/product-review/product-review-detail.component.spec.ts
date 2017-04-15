import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductReviewDetailComponent } from '../../../../../../main/webapp/app/entities/product-review/product-review-detail.component';
import { ProductReviewService } from '../../../../../../main/webapp/app/entities/product-review/product-review.service';
import { ProductReview } from '../../../../../../main/webapp/app/entities/product-review/product-review.model';

describe('Component Tests', () => {

    describe('ProductReview Management Detail Component', () => {
        let comp: ProductReviewDetailComponent;
        let fixture: ComponentFixture<ProductReviewDetailComponent>;
        let service: ProductReviewService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ProductReviewDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductReviewService,
                    EventManager
                ]
            }).overrideComponent(ProductReviewDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductReviewDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductReviewService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductReview(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productReview).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
