import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductReviewDescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/product-review-description/product-review-description-detail.component';
import { ProductReviewDescriptionService } from '../../../../../../main/webapp/app/entities/product-review-description/product-review-description.service';
import { ProductReviewDescription } from '../../../../../../main/webapp/app/entities/product-review-description/product-review-description.model';

describe('Component Tests', () => {

    describe('ProductReviewDescription Management Detail Component', () => {
        let comp: ProductReviewDescriptionDetailComponent;
        let fixture: ComponentFixture<ProductReviewDescriptionDetailComponent>;
        let service: ProductReviewDescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ProductReviewDescriptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductReviewDescriptionService,
                    EventManager
                ]
            }).overrideComponent(ProductReviewDescriptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductReviewDescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductReviewDescriptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductReviewDescription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productReviewDescription).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
